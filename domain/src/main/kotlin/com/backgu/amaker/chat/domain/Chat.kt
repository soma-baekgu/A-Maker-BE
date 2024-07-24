package com.backgu.amaker.chat.domain

import com.backgu.amaker.event.domain.ReplyEvent
import java.time.LocalDateTime

class Chat(
    val id: Long = 0L,
    val userId: String,
    val chatRoomId: Long,
    var content: String,
    val chatType: ChatType,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun crateReplyEvent(
        deadLine: LocalDateTime,
        notificationStartHour: Int,
        notificationStartMinute: Int,
        notificationInterval: Int,
        eventDetails: String,
    ) = ReplyEvent(
        id = id,
        eventTitle = content,
        deadLine = deadLine,
        notificationStartTime =
            deadLine
                .minusHours(notificationStartHour.toLong())
                .minusMinutes(notificationStartMinute.toLong()),
        notificationInterval = notificationInterval,
        eventDetails = eventDetails,
    )
}
