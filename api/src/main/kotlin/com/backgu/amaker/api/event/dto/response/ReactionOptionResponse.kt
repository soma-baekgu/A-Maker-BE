package com.backgu.amaker.api.event.dto.response

import com.backgu.amaker.api.event.dto.ReactionOptionDto
import io.swagger.v3.oas.annotations.media.Schema

data class ReactionOptionResponse(
    @Schema(description = "선택지 id", example = "1")
    val id: Long,
    @Schema(description = "이벤트 id", example = "1")
    val eventId: Long,
    @Schema(description = "내용", example = "옵션 1")
    val content: String,
) {
    companion object {
        fun of(reactionOptionDto: ReactionOptionDto) =
            ReactionOptionResponse(
                id = reactionOptionDto.id,
                eventId = reactionOptionDto.eventId,
                content = reactionOptionDto.content,
            )
    }
}
