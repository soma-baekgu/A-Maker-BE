package com.backgu.amaker.api.chat.dto.response

import com.backgu.amaker.api.chat.dto.ChatWithUserDto
import com.backgu.amaker.api.user.dto.response.UserResponse
import com.backgu.amaker.domain.chat.ChatType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class ChatWithUserResponse(
    @Schema(description = "채팅 ID", example = "1")
    val id: Long,
    val user: UserResponse,
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
        fun of(chatWithUserDto: ChatWithUserDto): ChatWithUserResponse =
            ChatWithUserResponse(
                id = chatWithUserDto.id,
                user = UserResponse.of(chatWithUserDto.user),
                chatRoomId = chatWithUserDto.chatRoomId,
                content = chatWithUserDto.content,
                chatType = chatWithUserDto.chatType,
                createdAt = chatWithUserDto.createdAt,
                updatedAt = chatWithUserDto.updatedAt,
            )
    }
}
