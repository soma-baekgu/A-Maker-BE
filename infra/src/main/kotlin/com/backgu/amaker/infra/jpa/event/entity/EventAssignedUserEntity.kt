package com.backgu.amaker.infra.jpa.event.entity

import com.backgu.amaker.domain.event.EventAssignedUser
import com.backgu.amaker.infra.jpa.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "EventAssignedUser")
@Table(name = "event_assigned_user")
class EventAssignedUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val eventId: Long,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false)
    var isFinished: Boolean = false,
) : BaseTimeEntity() {
    fun toDomain(): EventAssignedUser =
        EventAssignedUser(
            id = id,
            eventId = eventId,
            userId = userId,
            isFinished = isFinished,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    companion object {
        fun of(eventAssigned: EventAssignedUser): EventAssignedUserEntity =
            EventAssignedUserEntity(
                id = eventAssigned.id,
                eventId = eventAssigned.eventId,
                userId = eventAssigned.userId,
                isFinished = eventAssigned.isFinished,
            )
    }
}
