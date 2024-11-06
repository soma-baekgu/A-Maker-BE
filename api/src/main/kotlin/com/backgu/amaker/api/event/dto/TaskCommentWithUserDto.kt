package com.backgu.amaker.api.event.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.event.TaskComment
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

data class TaskCommentWithUserDto(
    val id: Long,
    val userId: String,
    val eventId: Long,
    val path: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userDto: UserDto,
) {
    companion object {
        fun of(
            replyComment: TaskComment,
            user: User,
        ) = TaskCommentWithUserDto(
            id = replyComment.id,
            userId = replyComment.userId,
            eventId = replyComment.eventId,
            path = replyComment.path,
            createdAt = replyComment.createdAt,
            updatedAt = replyComment.updatedAt,
            userDto = UserDto.of(user),
        )
    }
}
