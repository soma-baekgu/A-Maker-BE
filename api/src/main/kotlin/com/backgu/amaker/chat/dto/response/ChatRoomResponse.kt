package com.backgu.amaker.chat.dto.response

import com.backgu.amaker.chat.dto.ChatRoomDto
import io.swagger.v3.oas.annotations.media.Schema

data class ChatRoomResponse(
    @Schema(description = "채팅방 id", example = "1")
    val chatRoomId: Long,
    @Schema(description = "워크스페이스 id", example = "1")
    val workspaceId: Long,
    @Schema(description = "채팅방 종류", example = "DEFAULT")
    val chatRoomType: String,
) {
    companion object {
        fun of(chatRoomDto: ChatRoomDto): ChatRoomResponse =
            ChatRoomResponse(
                chatRoomId = chatRoomDto.chatRoomId,
                workspaceId = chatRoomDto.workspaceId,
                chatRoomType = chatRoomDto.chatRoomType,
            )
    }
}
