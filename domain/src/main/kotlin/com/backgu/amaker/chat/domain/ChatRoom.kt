package com.backgu.amaker.chat.domain

import com.backgu.amaker.common.domain.BaseTime
import com.backgu.amaker.user.domain.User

class ChatRoom(
    val id: Long = 0L,
    val workspaceId: Long,
    val chatRoomType: ChatRoomType,
) : BaseTime() {
    fun addUser(user: User): ChatRoomUser = ChatRoomUser(userId = user.id, chatRoomId = id)
}
