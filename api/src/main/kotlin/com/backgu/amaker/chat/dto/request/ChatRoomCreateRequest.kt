package com.backgu.amaker.chat.dto.request

import com.backgu.amaker.chat.dto.ChatRoomCreateDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

class ChatRoomCreateRequest(
    @field:NotBlank(message = "채팅방 이름을 입력해주세요.")
    @Schema(description = "채팅방 이름", example = "자료조사방")
    val name: String,
) {
    fun toDto() =
        ChatRoomCreateDto(
            name = name,
        )
}
