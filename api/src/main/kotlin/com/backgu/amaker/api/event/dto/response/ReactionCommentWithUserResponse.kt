package com.backgu.amaker.api.event.dto.response

import com.backgu.amaker.api.event.dto.ReactionCommentWithUserDto
import com.backgu.amaker.api.user.dto.response.UserResponse
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class ReactionCommentWithUserResponse(
    @Schema(description = "리액션 응답 id", example = "1")
    val id: Long,
    @Schema(description = "생성 시간", example = "2024-07-02T19:56:05.624+09:00")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "수정 시간", example = "2024-07-02T19:56:05.624+09:00")
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userDto: UserResponse,
) {
    companion object {
        fun of(reactionComment: ReactionCommentWithUserDto) =
            ReactionCommentWithUserResponse(
                id = reactionComment.id,
                createdAt = reactionComment.createdAt,
                updatedAt = reactionComment.updatedAt,
                userDto = UserResponse.of(reactionComment.userDto),
            )
    }
}
