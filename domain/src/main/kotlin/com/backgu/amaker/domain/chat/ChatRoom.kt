package com.backgu.amaker.domain.chat

import com.backgu.amaker.domain.user.User
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
        chatType: ChatType = ChatType.GENERAL,
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
