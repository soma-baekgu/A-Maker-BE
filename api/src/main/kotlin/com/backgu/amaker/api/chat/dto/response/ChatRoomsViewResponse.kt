package com.backgu.amaker.api.chat.dto.response

import com.backgu.amaker.api.chat.dto.ChatRoomsViewDto
import io.swagger.v3.oas.annotations.media.Schema

data class ChatRoomsViewResponse(
    @Schema(description = "채팅방 리스트")
    val chatRooms: List<ChatRoomWithUserAndLastChatResponse>,
    @Schema(description = "총 안읽은 채팅 수", example = "10")
    val totalUnreadChatCount: Long,
) {
    companion object {
        fun of(chatRoomsViewDto: ChatRoomsViewDto): ChatRoomsViewResponse =
            ChatRoomsViewResponse(
                chatRooms =
                    chatRoomsViewDto.chatRooms
                        .map { ChatRoomWithUserAndLastChatResponse.of(it) }
                        .sortedByDescending { it.lastChat?.id ?: Long.MIN_VALUE },
                totalUnreadChatCount = chatRoomsViewDto.totalUnreadChatCount,
            )
    }
}
