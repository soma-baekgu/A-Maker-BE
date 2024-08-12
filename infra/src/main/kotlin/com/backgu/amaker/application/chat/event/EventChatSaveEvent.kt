package com.backgu.amaker.application.chat.event

import com.backgu.amaker.domain.chat.EventChatWithUser

data class EventChatSaveEvent(
    val chatRoomId: Long,
    val eventChatWithUser: EventChatWithUser,
) {
    companion object {
        fun of(
            chatRoomId: Long,
            eventChatWithUser: EventChatWithUser,
        ) = EventChatSaveEvent(
            chatRoomId = chatRoomId,
            eventChatWithUser = eventChatWithUser,
        )
    }
}
