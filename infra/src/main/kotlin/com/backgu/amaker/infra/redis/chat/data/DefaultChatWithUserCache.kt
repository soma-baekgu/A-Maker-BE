package com.backgu.amaker.infra.redis.chat.data

import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.domain.chat.DefaultChatWithUser
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

class DefaultChatWithUserCache(
    override val id: Long = 0L,
    override val chatRoomId: Long,
    override val content: String,
    override val chatType: ChatType,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    override val userId: String,
) : ChatWithUserCache<String> {
    fun toDomain(user: User) =
        DefaultChatWithUser(
            id = id,
            chatRoomId = chatRoomId,
            content = content,
            chatType = chatType,
            createdAt = createdAt,
            updatedAt = updatedAt,
            user = user,
        )

    companion object {
        fun of(
            chat: Chat,
            user: User,
        ): DefaultChatWithUserCache =
            DefaultChatWithUserCache(
                id = chat.id,
                chatRoomId = chat.chatRoomId,
                content = chat.content,
                chatType = chat.chatType,
                createdAt = chat.createdAt,
                updatedAt = chat.updatedAt,
                userId = user.id,
            )

        fun of(chatWithUser: DefaultChatWithUser) =
            DefaultChatWithUserCache(
                id = chatWithUser.id,
                chatRoomId = chatWithUser.chatRoomId,
                content = chatWithUser.content,
                chatType = chatWithUser.chatType,
                createdAt = chatWithUser.createdAt,
                updatedAt = chatWithUser.updatedAt,
                userId = chatWithUser.user.id,
            )
    }
}
