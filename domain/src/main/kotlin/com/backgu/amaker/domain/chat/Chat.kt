package com.backgu.amaker.domain.chat

import com.backgu.amaker.domain.event.ReplyEvent
import java.time.LocalDateTime

open class Chat(
    val id: Long = 0L,
    val userId: String,
    val chatRoomId: Long,
    var content: String,
    val chatType: ChatType,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun createReplyEvent(
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
