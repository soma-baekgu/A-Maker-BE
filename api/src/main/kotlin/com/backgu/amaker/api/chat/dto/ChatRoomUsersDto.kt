package com.backgu.amaker.api.chat.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.user.User

data class ChatRoomUsersDto(
    val chatRoomId: Long,
    val chatRoomType: String,
    val chatRoomName: String,
    val users: List<UserDto>,
) {
    companion object {
        fun of(
            chatRoom: ChatRoom,
            users: List<User>,
        ): ChatRoomUsersDto =
            ChatRoomUsersDto(
                chatRoomId = chatRoom.id,
                chatRoomType = chatRoom.chatRoomType.name,
                chatRoomName = chatRoom.name,
                users = users.map { UserDto.of(it) },
            )
    }
}
