package com.backgu.amaker.api.event.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.event.ReplyComment
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

data class ReplyCommentWithUserDto(
    val id: Long,
    val userId: String,
    val eventId: Long,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userDto: UserDto,
) {
    companion object {
        fun of(
            replyComment: ReplyComment,
            user: User,
        ) = ReplyCommentWithUserDto(
            id = replyComment.id,
            userId = replyComment.userId,
            eventId = replyComment.eventId,
            content = replyComment.content,
            createdAt = replyComment.createdAt,
            updatedAt = replyComment.updatedAt,
            userDto = UserDto.of(user),
        )
    }
}
