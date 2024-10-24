package com.backgu.amaker.infra.jpa.event.entity

import com.backgu.amaker.domain.event.ReactionComment
import com.backgu.amaker.infra.jpa.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ReactionComment")
@Table(name = "reaction_comment")
class ReactionCommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false)
    val eventId: Long,
    @Column(nullable = false)
    var optionId: Long,
) : BaseTimeEntity() {
    fun toDomain(): ReactionComment =
        ReactionComment(
            id = id,
            userId = userId,
            eventId = eventId,
            optionId = optionId,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    companion object {
        fun of(reactionComment: ReactionComment): ReactionCommentEntity =
            ReactionCommentEntity(
                id = reactionComment.id,
                userId = reactionComment.userId,
                eventId = reactionComment.eventId,
                optionId = reactionComment.optionId,
            )
    }
}
