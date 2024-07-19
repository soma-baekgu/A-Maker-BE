package com.backgu.amaker.chat.dto

import com.backgu.amaker.user.dto.UserDto

class ChatRoomWithUserAndLastChatDto(
    val chatRoomId: Long,
    val chatRoomName: String,
    val participants: List<UserDto>,
    val lastChat: ChatWithUserDto?,
    val unreadChatCount: Long?,
) {
    companion object {
        fun of(
            chatRoomId: Long,
            chatRoomName: String,
            participants: List<UserDto>,
            lastChat: ChatWithUserDto?,
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
