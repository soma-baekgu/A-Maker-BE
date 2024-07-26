package com.backgu.amaker.api.chat.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatRoomUser
import com.backgu.amaker.domain.user.User

class ChatRoomWithUserDto(
    val chatRoomId: Long,
    val chatRoomName: String,
    val participants: List<UserDto>,
) {
    companion object {
        fun of(
            chatRoom: ChatRoom,
            chatRoomUser: List<ChatRoomUser>,
            participants: Map<String, User>,
        ): ChatRoomWithUserDto =
            ChatRoomWithUserDto(
                chatRoomId = chatRoom.id,
                chatRoomName = chatRoom.name,
                participants = chatRoomUser.mapNotNull { participants[it.userId] }.map { UserDto.of(it) },
            )
    }
}
