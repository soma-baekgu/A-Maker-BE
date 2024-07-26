package com.backgu.amaker.api.chat.dto.request

import com.backgu.amaker.api.chat.dto.ChatCreateDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class ChatCreateRequest(
    @field:NotBlank(message = "채팅 내용은 필수입니다.")
    @Schema(description = "내용", example = "안녕하세요!")
    val content: String,
) {
    fun toDto() =
        ChatCreateDto(
            content = content,
        )
}
