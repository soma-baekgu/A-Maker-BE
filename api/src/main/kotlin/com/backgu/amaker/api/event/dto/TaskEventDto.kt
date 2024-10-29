package com.backgu.amaker.api.event.dto

import com.backgu.amaker.domain.event.TaskEvent
import java.time.LocalDateTime

data class TaskEventDto(
    val id: Long,
    val eventTitle: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
) {
    companion object {
        fun of(reactionEvent: TaskEvent) =
            TaskEventDto(
                id = reactionEvent.id,
                eventTitle = reactionEvent.eventTitle,
                deadLine = reactionEvent.deadLine,
                notificationStartTime = reactionEvent.notificationStartTime,
                notificationInterval = reactionEvent.notificationInterval,
            )
    }
}
