package com.backgu.amaker.api.chat.dto

import com.backgu.amaker.api.user.dto.UserDto

class ChatRoomWithUserAndLastChatDto(
    val chatRoomId: Long,
    val chatRoomName: String,
    val participants: List<UserDto>,
    val lastChat: DefaultChatWithUserDto?,
    val unreadChatCount: Long?,
) {
    companion object {
        fun of(
            chatRoomId: Long,
            chatRoomName: String,
            participants: List<UserDto>,
            lastChat: DefaultChatWithUserDto?,
            unreadChatCount: Long?,
        ): ChatRoomWithUserAndLastChatDto =
            ChatRoomWithUserAndLastChatDto(
                chatRoomId = chatRoomId,
                chatRoomName = chatRoomName,
                participants = participants,
                lastChat = lastChat,
                unreadChatCount = unreadChatCount,
            )
    }
}
