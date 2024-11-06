package com.backgu.amaker.api.event.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.event.ReactionComment
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

data class ReactionCommentWithUserDto(
    val id: Long,
    val eventId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userDto: UserDto,
) {
    companion object {
        fun of(
            reactionComment: ReactionComment,
            user: User,
        ) = ReactionCommentWithUserDto(
            id = reactionComment.id,
            eventId = reactionComment.eventId,
            createdAt = reactionComment.createdAt,
            updatedAt = reactionComment.updatedAt,
            userDto = UserDto.of(user),
        )
    }
}
