package com.backgu.amaker.chat.domain

import java.time.LocalDateTime

class ChatRoomUser(
    val id: Long = 0L,
    val userId: String,
    val chatRoomId: Long,
    var lastReadChatId: Long? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun readLastChatOfChatRoom(chatRoom: ChatRoom): ChatRoomUser {
        lastReadChatId = chatRoom.lastChatId
        return this
    }
}
