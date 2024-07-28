package com.backgu.amaker.api.event.dto.request

import com.backgu.amaker.api.event.dto.ReplyCommentCreateDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class ReplyCommentCreateRequest(
    @field:NotBlank(message = "reply 응답 내용은 필수입니다.")
    @Schema(description = "내용", example = "좋아요!!")
    val content: String,
) {
    fun toDto() =
        ReplyCommentCreateDto(
            content = content,
        )
}
