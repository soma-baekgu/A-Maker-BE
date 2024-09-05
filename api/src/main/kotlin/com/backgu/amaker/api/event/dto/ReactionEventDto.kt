package com.backgu.amaker.api.event.dto

import com.backgu.amaker.domain.event.ReactionEvent
import com.backgu.amaker.domain.event.ReactionOption
import java.time.LocalDateTime

data class ReactionEventDto(
    val id: Long,
    val eventTitle: String,
    val options: List<String>,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
) {
    companion object {
        fun of(
            reactionEvent: ReactionEvent,
            options: List<ReactionOption>,
        ) = ReactionEventDto(
            id = reactionEvent.id,
            eventTitle = reactionEvent.eventTitle,
            options = options.map { it.content },
            deadLine = reactionEvent.deadLine,
            notificationStartTime = reactionEvent.notificationStartTime,
            notificationInterval = reactionEvent.notificationInterval,
        )
    }
}
