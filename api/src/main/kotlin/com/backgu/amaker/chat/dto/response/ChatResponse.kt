package com.backgu.amaker.chat.dto.response

import com.backgu.amaker.chat.domain.ChatType
import com.backgu.amaker.chat.dto.ChatDto
import java.time.LocalDateTime

data class ChatResponse(
    val id: Long,
    val userId: String,
    val chatRoomId: Long,
    var content: String,
    val chatType: ChatType,
    val createdAt: LocalDateTime,
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
