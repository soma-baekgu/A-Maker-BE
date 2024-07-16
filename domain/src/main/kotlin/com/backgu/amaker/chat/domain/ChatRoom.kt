package com.backgu.amaker.chat.domain

import com.backgu.amaker.common.domain.BaseTime
import com.backgu.amaker.user.domain.User

class ChatRoom(
    val id: Long = 0L,
    val workspaceId: Long,
    val chatRoomType: ChatRoomType,
    var lastChatId: Long? = null,
) : BaseTime() {
    fun addUser(user: User): ChatRoomUser = ChatRoomUser(userId = user.id, chatRoomId = id)

    fun createGeneralChat(
        user: User,
        chatRoom: ChatRoom,
        content: String,
    ) = Chat(
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
