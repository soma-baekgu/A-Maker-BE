package com.backgu.amaker.api.chat.dto.response

import com.backgu.amaker.api.chat.dto.ChatListDto
import io.swagger.v3.oas.annotations.media.Schema

class ChatListResponse(
    @Schema(description = "채팅방 ID", example = "1")
    val chatRoomId: Long,
    @Schema(description = "채팅 커서", example = "101")
    val cursor: Long,
    @Schema(description = "읽어올 채팅의 개수", example = "100", defaultValue = "20")
    val size: Int,
    val chatList: List<ChatWithUserResponse<*>>,
    @Schema(description = "다음 요청에 대한 커서", example = "100", defaultValue = "121")
    val nextCursor: Long,
) {
    companion object {
        fun of(chatListDto: ChatListDto) =
            ChatListResponse(
                chatRoomId = chatListDto.chatRoomId,
                cursor = chatListDto.cursor,
                size = chatListDto.size,
                chatList = chatListDto.chatList.map { ChatWithUserResponse.of(it) },
                nextCursor = chatListDto.chatList.first().id,
            )
    }
}
