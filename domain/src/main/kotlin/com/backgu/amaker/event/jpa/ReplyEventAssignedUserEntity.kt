package com.backgu.amaker.event.jpa

import com.backgu.amaker.common.jpa.BaseTimeEntity
import com.backgu.amaker.event.domain.ReplyEventAssignedUser
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class ReplyEventAssignedUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val eventId: Long,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false)
    val isFinished: Boolean = false,
) : BaseTimeEntity() {
    fun toDomain(): ReplyEventAssignedUser =
        ReplyEventAssignedUser(
            id = id,
            eventId = eventId,
            userId = userId,
            isFinished = isFinished,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    companion object {
        fun of(replyEventAssigned: ReplyEventAssignedUser): ReplyEventAssignedUserEntity =
            ReplyEventAssignedUserEntity(
                id = replyEventAssigned.id,
                eventId = replyEventAssigned.eventId,
                userId = replyEventAssigned.userId,
            )
    }
}
