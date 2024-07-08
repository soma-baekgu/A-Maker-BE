package com.backgu.amaker.chat.dto.response

import com.backgu.amaker.chat.domain.ChatType
import com.backgu.amaker.chat.dto.ChatDto
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class ChatResponse(
    @Schema(description = "채팅 ID", example = "1")
    val id: Long,
    @Schema(description = "유저 ID(현재 UUID)", example = "0000-0000-0000-0000000")
    val userId: String,
    @Schema(description = "채팅방 ID", example = "1")
    val chatRoomId: Long,
    @Schema(description = "채팅 내용", example = "안녕하세요")
    var content: String,
    @Schema(description = "채팅 타입(GENERAL, REPLY, REACTION, TASK)", example = "GENERAL")
    val chatType: ChatType,
    @Schema(description = "생성일시", example = "2021-05-29T00:00:00")
    val createdAt: LocalDateTime,
    @Schema(description = "수정일시", example = "2021-05-29T00:00:00")
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun of(chatDto: ChatDto): ChatResponse =
            ChatResponse(
                id = chatDto.id,
                userId = chatDto.userId,
                chatRoomId = chatDto.chatRoomId,
                content = chatDto.content,
                chatType = chatDto.chatType,
                createdAt = chatDto.createdAt,
                updatedAt = chatDto.updatedAt,
            )
    }
}
