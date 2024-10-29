package com.backgu.amaker.infra.jpa.event.entity

import com.backgu.amaker.domain.event.TaskEvent
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "TaskEvent")
@Table(name = "task_event")
@DiscriminatorValue(value = "TASK")
class TaskEventEntity(
    @Column(nullable = false)
    val eventDetails: String,
    id: Long,
    eventTitle: String,
    deadLine: LocalDateTime,
    notificationStartTime: LocalDateTime,
    notificationInterval: Int,
) : EventEntity(
        id = id,
        eventTitle = eventTitle,
        deadLine = deadLine,
        notificationStartTime = notificationStartTime,
        notificationInterval = notificationInterval,
    ) {
    override fun toDomain(): TaskEvent =
        TaskEvent(
            id = id,
            eventTitle = eventTitle,
            eventDetails = eventDetails,
            deadLine = deadLine,
            notificationStartTime = notificationStartTime,
            notificationInterval = notificationInterval,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    companion object {
        fun of(taskEvent: TaskEvent) =
            TaskEventEntity(
                id = taskEvent.id,
                eventTitle = taskEvent.eventTitle,
                eventDetails = taskEvent.eventDetails,
                deadLine = taskEvent.deadLine,
                notificationStartTime = taskEvent.notificationStartTime,
                notificationInterval = taskEvent.notificationInterval,
            )
    }
}
