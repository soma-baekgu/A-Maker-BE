package com.backgu.amaker.api.event.dto.response

import com.backgu.amaker.api.event.dto.TaskCommentWithUserDto
import com.backgu.amaker.api.user.dto.response.UserResponse
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class TaskCommentWithUserResponse(
    @Schema(description = "태스크 id", example = "1")
    val id: Long,
    @Schema(description = "유저 id", example = "2")
    val userId: String,
    @Schema(description = "이벤트 id", example = "2")
    val eventId: Long,
    @Schema(description = "경로", example = "https://a-maker.com/hi.png")
    val path: String,
    @Schema(description = "생성 시간", example = "2024-07-02T19:56:05.624+09:00")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "수정 시간", example = "2024-07-02T19:56:05.624+09:00")
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "유저 응답")
    val userResponse: UserResponse,
) {
    companion object {
        fun of(taskCommentWithUserDto: TaskCommentWithUserDto) =
            TaskCommentWithUserResponse(
                id = taskCommentWithUserDto.id,
                userId = taskCommentWithUserDto.userId,
                eventId = taskCommentWithUserDto.eventId,
                path = taskCommentWithUserDto.path,
                createdAt = taskCommentWithUserDto.createdAt,
                updatedAt = taskCommentWithUserDto.updatedAt,
                userResponse = UserResponse.of(taskCommentWithUserDto.userDto),
            )
    }
}
