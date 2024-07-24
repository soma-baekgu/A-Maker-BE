package com.backgu.amaker.chat.dto

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomUser
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.dto.UserDto

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
