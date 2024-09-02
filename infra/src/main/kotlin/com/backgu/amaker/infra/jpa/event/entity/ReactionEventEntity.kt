package com.backgu.amaker.infra.jpa.event.entity

import com.backgu.amaker.domain.event.ReactionEvent
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ReactionEvent")
@Table(name = "reaction_event")
@DiscriminatorValue(value = "REACTION")
class ReactionEventEntity(
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
    override fun toDomain(): ReactionEvent =
        ReactionEvent(
            id = id,
            eventTitle = eventTitle,
            deadLine = deadLine,
            notificationStartTime = notificationStartTime,
            notificationInterval = notificationInterval,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    companion object {
        fun of(reactionEvent: ReactionEvent) =
            ReactionEventEntity(
                id = reactionEvent.id,
                eventTitle = reactionEvent.eventTitle,
                deadLine = reactionEvent.deadLine,
                notificationStartTime = reactionEvent.notificationStartTime,
                notificationInterval = reactionEvent.notificationInterval,
            )
    }
}
