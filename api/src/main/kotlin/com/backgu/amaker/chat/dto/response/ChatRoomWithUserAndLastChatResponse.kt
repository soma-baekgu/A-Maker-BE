package com.backgu.amaker.chat.dto.response

import com.backgu.amaker.chat.dto.ChatRoomWithUserAndLastChatDto
import com.backgu.amaker.user.dto.response.UserResponse
import io.swagger.v3.oas.annotations.media.Schema

data class ChatRoomWithUserAndLastChatResponse(
    @Schema(description = "채팅방 id", example = "1")
    val chatRoomId: Long,
    @Schema(description = "채팅방 이름", example = "채팅방")
    val chatRoomName: String,
    @Schema(description = "채팅방 참여자")
    val participants: List<UserResponse>,
    @Schema(description = "마지막 채팅")
    val lastChat: ChatWithUserResponse<*>?,
    @Schema(description = "안읽은 채팅 수", example = "10")
    val unreadChatCount: Long?,
) {
    companion object {
        fun of(chatRoomWithUserAndLastChatDto: ChatRoomWithUserAndLastChatDto): ChatRoomWithUserAndLastChatResponse =
            ChatRoomWithUserAndLastChatResponse(
                chatRoomId = chatRoomWithUserAndLastChatDto.chatRoomId,
                chatRoomName = chatRoomWithUserAndLastChatDto.chatRoomName,
                participants = chatRoomWithUserAndLastChatDto.participants.map { UserResponse.of(it) },
                lastChat = chatRoomWithUserAndLastChatDto.lastChat?.let { ChatWithUserResponse.of(it) },
                unreadChatCount = chatRoomWithUserAndLastChatDto.unreadChatCount,
            )
    }
}
