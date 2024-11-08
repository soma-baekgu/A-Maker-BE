package com.backgu.amaker.api.event.dto.response

import com.backgu.amaker.api.event.dto.ReactionOptionWithCommentDto
import io.swagger.v3.oas.annotations.media.Schema

data class ReactionOptionWithCommentResponse(
    @Schema(description = "리액션 응답 옵션 id", example = "1")
    val id: Long,
    @Schema(description = "이벤트 id", example = "2")
    val eventId: Long,
    @Schema(description = "응답 내용", example = "좋네용!")
    val content: String,
    val comments: List<ReactionCommentWithUserResponse>,
) {
    companion object {
        fun of(reactionOptionWithComment: ReactionOptionWithCommentDto) =
            ReactionOptionWithCommentResponse(
                id = reactionOptionWithComment.id,
                eventId = reactionOptionWithComment.eventId,
                content = reactionOptionWithComment.content,
                comments = reactionOptionWithComment.comments.map { ReactionCommentWithUserResponse.of(it) },
            )
    }
}
