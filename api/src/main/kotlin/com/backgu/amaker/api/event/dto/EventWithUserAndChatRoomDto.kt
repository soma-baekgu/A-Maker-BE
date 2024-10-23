package com.backgu.amaker.api.event.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.event.Event
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

data class EventWithUserAndChatRoomDto(
    val id: Long,
    val chatRoomId: Long,
    val eventTitle: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val users: List<UserDto>,
    val finishedCount: Int,
    val totalAssignedCount: Int,
) {
    companion object {
        fun of(
            event: Event,
            chat: Chat,
            users: List<User>,
            finishedCount: Int = 0,
        ): EventWithUserAndChatRoomDto =
            EventWithUserAndChatRoomDto(
                id = event.id,
                chatRoomId = chat.chatRoomId,
                eventTitle = event.eventTitle,
                deadLine = event.deadLine,
                notificationStartTime = event.notificationStartTime,
                notificationInterval = event.notificationInterval,
                users = users.map { UserDto.of(it) },
                finishedCount = finishedCount,
                totalAssignedCount = users.size,
            )
    }
}
