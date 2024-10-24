package com.backgu.amaker.api.event.dto

import com.backgu.amaker.domain.event.ReactionComment
import java.time.LocalDateTime

data class ReactionCommentDto(
    val id: Long,
    val userId: String,
    val eventId: Long,
    val optionId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun of(reactionComment: ReactionComment) =
            ReactionCommentDto(
                id = reactionComment.id,
                userId = reactionComment.userId,
                eventId = reactionComment.eventId,
                optionId = reactionComment.optionId,
                createdAt = reactionComment.createdAt,
                updatedAt = reactionComment.updatedAt,
            )
    }
}
