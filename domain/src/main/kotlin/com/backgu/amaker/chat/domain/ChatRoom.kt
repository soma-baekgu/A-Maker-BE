package com.backgu.amaker.chat.domain

import com.backgu.amaker.user.domain.User
import java.time.LocalDateTime

class ChatRoom(
    val id: Long = 0L,
    var name: String,
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
        content: String,
        chatType: ChatType,
    ): Chat =
        Chat(
            userId = user.id,
            chatRoomId = this.id,
            content = content,
            chatType = chatType,
        )

    fun updateLastChatId(chat: Chat): ChatRoom {
        lastChatId = chat.id
        return this
    }
}
