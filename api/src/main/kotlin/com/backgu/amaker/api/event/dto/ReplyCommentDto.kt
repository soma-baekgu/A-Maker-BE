package com.backgu.amaker.api.event.dto

import com.backgu.amaker.domain.event.ReplyComment
import java.time.LocalDateTime

class ReplyCommentDto(
    val id: Long,
    val userId: String,
    val eventId: Long,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun of(replyComment: ReplyComment) =
            ReplyCommentDto(
                id = replyComment.id,
                userId = replyComment.userId,
                eventId = replyComment.eventId,
                content = replyComment.content,
                createdAt = replyComment.createdAt,
                updatedAt = replyComment.updatedAt,
            )
    }
}
