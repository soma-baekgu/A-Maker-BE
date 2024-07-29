package com.backgu.amaker.api.event.dto.response

import com.backgu.amaker.api.event.dto.ReplyCommentWithUserDto
import com.backgu.amaker.api.user.dto.response.UserResponse
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class ReplyCommentWithUserResponse(
    @Schema(description = "리플라이 응답 id", example = "1")
    val id: Long,
    @Schema(description = "유저 id", example = "2")
    val userId: String,
    @Schema(description = "이벤트 id", example = "2")
    val eventId: Long,
    @Schema(description = "응답 내용", example = "좋네용!")
    val content: String,
    @Schema(description = "생성 시간", example = "2024-07-02T19:56:05.624+09:00")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "수정 시간", example = "2024-07-02T19:56:05.624+09:00")
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "유저 응답")
    val userResponse: UserResponse,
) {
    companion object {
        fun of(replyCommentWithUserDto: ReplyCommentWithUserDto) =
            ReplyCommentWithUserResponse(
                id = replyCommentWithUserDto.id,
                userId = replyCommentWithUserDto.userId,
                eventId = replyCommentWithUserDto.eventId,
                content = replyCommentWithUserDto.content,
                createdAt = replyCommentWithUserDto.createdAt,
                updatedAt = replyCommentWithUserDto.updatedAt,
                userResponse = UserResponse.of(replyCommentWithUserDto.userDto),
            )
    }
}
