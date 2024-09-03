package com.backgu.amaker.api.event.dto

import com.backgu.amaker.domain.event.ReactionOption

data class ReactionOptionDto(
    val id: Long,
    val eventId: Long,
    val content: String,
) {
    companion object {
        fun of(reactionOption: ReactionOption) =
            ReactionOptionDto(
                id = reactionOption.id,
                eventId = reactionOption.eventId,
                content = reactionOption.content,
            )
    }
}
