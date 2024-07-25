package com.backgu.amaker.chat.dto.request

import com.backgu.amaker.chat.dto.ChatCreateDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class FileChatCreateRequest(
    @field:NotBlank(message = "파일 경로를 입력해주세요.")
    @Schema(description = "등록할 파일의 경로", example = "https://a-maker.co.kr/hi.png")
    val path: String,
) {
    fun toDto() =
        ChatCreateDto(
            content = path,
        )
}
