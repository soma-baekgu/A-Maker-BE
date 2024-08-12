package com.backgu.amaker.infra.redis.chat.data

import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.domain.chat.EventChatWithUser
import com.backgu.amaker.domain.event.EventWithUser
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

class EventChatWithUserCache(
    override val id: Long,
    override val chatRoomId: Long,
    override val content: EventWithUserCache,
    override val chatType: ChatType,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    override val userId: String,
) : ChatWithUserCache<EventWithUserCache> {
    fun toDomain(
        user: User,
        eventWithUser: EventWithUser,
    ) = EventChatWithUser(
        id = id,
        chatRoomId = chatRoomId,
        content = eventWithUser,
        chatType = chatType,
        createdAt = createdAt,
        updatedAt = updatedAt,
        user = user,
    )

    fun updateFinishedCount(finishedCount: Int): EventChatWithUserCache {
        content.updateFinishedCount(finishedCount)
        return this
    }

    companion object {
        fun of(chat: EventChatWithUser): EventChatWithUserCache =
            EventChatWithUserCache(
                id = chat.id,
                chatRoomId = chat.chatRoomId,
                content = EventWithUserCache.of(chat.content),
                chatType = chat.chatType,
                createdAt = chat.createdAt,
                updatedAt = chat.updatedAt,
                userId = chat.user.id,
            )
    }
}
