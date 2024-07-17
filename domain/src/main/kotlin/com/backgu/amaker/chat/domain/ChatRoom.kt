package com.backgu.amaker.chat.domain

import com.backgu.amaker.user.domain.User
import java.time.LocalDateTime

class ChatRoom(
    val id: Long = 0L,
    val workspaceId: Long,
    val chatRoomType: ChatRoomType,
    var lastChatId: Long? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun addUser(user: User): ChatRoomUser =
        ChatRoomUser(
            userId = user.id,
            chatRoomId = id,
        )

    fun createChat(
        user: User,
        chatRoom: ChatRoom,
        content: String,
    ): Chat =
        Chat(
            userId = user.id,
            chatRoomId = chatRoom.id,
            content = content,
            chatType = ChatType.GENERAL,
        )

    fun updateLastChatId(chat: Chat): ChatRoom {
        lastChatId = chat.id
        return this
    }
}
