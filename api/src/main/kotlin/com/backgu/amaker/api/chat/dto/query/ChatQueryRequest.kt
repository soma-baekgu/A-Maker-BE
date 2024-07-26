package com.backgu.amaker.api.chat.dto.query

import com.backgu.amaker.api.chat.dto.ChatQuery
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

data class ChatQueryRequest(
    @field:NotNull(message = "채팅을 조회할 수 없습니다.")
    @Schema(description = "채팅 커서", example = "101")
    val cursor: Long,
    @Schema(description = "읽어올 채팅의 개수", example = "100", defaultValue = "20")
    val size: Int = 20,
) {
    fun toDto(chatRoomId: Long): ChatQuery =
        ChatQuery(
            cursor = cursor,
            size = size,
            chatRoomId = chatRoomId,
        )
}
