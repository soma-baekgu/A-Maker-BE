package com.backgu.amaker.api.event.dto

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.event.ReactionComment
import com.backgu.amaker.domain.event.ReactionOption
import com.backgu.amaker.domain.user.User

data class ReactionOptionWithCommentDto(
    val id: Long,
    val eventId: Long,
    val content: String,
    val comments: List<ReactionCommentWithUserDto>,
) {
    companion object {
        fun of(
            reactionOption: ReactionOption,
            reactionComment: List<ReactionComment>,
            userMap: Map<String, User>,
        ) = ReactionOptionWithCommentDto(
            id = reactionOption.id,
            eventId = reactionOption.eventId,
            content = reactionOption.content,
            comments =
                reactionComment.map {
                    ReactionCommentWithUserDto.of(
                        it,
                        userMap[it.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND),
                    )
                },
        )
    }
}
