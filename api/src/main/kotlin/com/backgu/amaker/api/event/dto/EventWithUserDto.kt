package com.backgu.amaker.api.event.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.event.Event
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

data class EventWithUserDto(
    val id: Long,
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
            users: List<User>,
            finishedCount: Int,
        ): EventWithUserDto =
            EventWithUserDto(
                id = event.id,
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
