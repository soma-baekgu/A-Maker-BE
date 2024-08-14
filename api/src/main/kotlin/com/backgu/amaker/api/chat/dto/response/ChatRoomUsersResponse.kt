package com.backgu.amaker.api.chat.dto.response

import com.backgu.amaker.api.chat.dto.ChatRoomUsersDto
import com.backgu.amaker.api.user.dto.response.UserResponse
import io.swagger.v3.oas.annotations.media.Schema

data class ChatRoomUsersResponse(
    @Schema(description = "채팅방 id", example = "1")
    val chatRoomId: Long,
    @Schema(description = "채팅방 타입", example = "DEFAULT")
    val chatRoomType: String,
    @Schema(description = "채팅방 이름", example = "기본 채팅방")
    val chatRoomName: String,
    @Schema(description = "채팅방 사용자 리스트")
    val users: List<UserResponse>,
) {
    companion object {
        fun of(chatRoomUsersDto: ChatRoomUsersDto): ChatRoomUsersResponse =
            ChatRoomUsersResponse(
                chatRoomId = chatRoomUsersDto.chatRoomId,
                chatRoomType = chatRoomUsersDto.chatRoomType,
                chatRoomName = chatRoomUsersDto.chatRoomName,
                users = chatRoomUsersDto.users.map { UserResponse.of(it) },
            )
    }
}
