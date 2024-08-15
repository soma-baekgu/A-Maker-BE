package com.backgu.amaker.application.chat.event

import com.backgu.amaker.domain.chat.DefaultChatWithUser

data class DefaultChatSaveEvent(
    val chatRoomId: Long,
    val defaultChatWithUser: DefaultChatWithUser,
) {
    companion object {
        fun of(
            chatRoomId: Long,
            defaultChatWithUser: DefaultChatWithUser,
        ) = DefaultChatSaveEvent(
            chatRoomId = chatRoomId,
            defaultChatWithUser = defaultChatWithUser,
        )
    }
}
