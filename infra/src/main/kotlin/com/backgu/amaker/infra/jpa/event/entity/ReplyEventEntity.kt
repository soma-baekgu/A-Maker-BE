package com.backgu.amaker.infra.jpa.event.entity

import com.backgu.amaker.domain.event.ReplyEvent
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ReplyEvent")
@Table(name = "reply_event")
@DiscriminatorValue(value = "REPLY")
class ReplyEventEntity(
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
    override fun toDomain(): ReplyEvent =
        ReplyEvent(
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
        fun of(replyEvent: ReplyEvent) =
            ReplyEventEntity(
                id = replyEvent.id,
                eventTitle = replyEvent.eventTitle,
                eventDetails = replyEvent.eventDetails,
                deadLine = replyEvent.deadLine,
                notificationStartTime = replyEvent.notificationStartTime,
                notificationInterval = replyEvent.notificationInterval,
            )
    }
}
