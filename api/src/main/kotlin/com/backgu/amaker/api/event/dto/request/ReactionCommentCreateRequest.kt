package com.backgu.amaker.api.event.dto.request

import com.backgu.amaker.api.event.dto.ReactionCommentCreateDto
import io.swagger.v3.oas.annotations.media.Schema

data class ReactionCommentCreateRequest(
    @Schema(description = "option id", example = "1")
    val optionId: Long,
) {
    fun toDto() =
        ReactionCommentCreateDto(
            optionId = optionId,
        )
}
