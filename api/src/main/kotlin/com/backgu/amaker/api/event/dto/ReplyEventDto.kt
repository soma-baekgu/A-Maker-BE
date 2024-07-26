package com.backgu.amaker.api.event.dto

import com.backgu.amaker.domain.event.ReplyEvent
import java.time.LocalDateTime

class ReplyEventDto(
    val id: Long,
    val eventTitle: String,
    val eventDetails: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
) {
    companion object {
        fun of(replyEvent: ReplyEvent) =
            ReplyEventDto(
                id = replyEvent.id,
                eventTitle = replyEvent.eventTitle,
                eventDetails = replyEvent.eventDetails,
                deadLine = replyEvent.deadLine,
                notificationStartTime = replyEvent.notificationStartTime,
                notificationInterval = replyEvent.notificationInterval,
            )
    }
}
