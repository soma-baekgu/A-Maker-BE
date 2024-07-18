package com.backgu.amaker.chat.dto

import com.backgu.amaker.chat.domain.ChatRoom

data class ChatRoomDto(
    val chatRoomId: Long,
    val workspaceId: Long,
    val chatRoomType: String,
    val chatRoomName: String,
) {
    companion object {
        fun of(chatRoom: ChatRoom): ChatRoomDto =
            ChatRoomDto(
                chatRoomId = chatRoom.id,
                workspaceId = chatRoom.workspaceId,
                chatRoomType = chatRoom.chatRoomType.name,
                chatRoomName = chatRoom.name,
            )
    }
}
