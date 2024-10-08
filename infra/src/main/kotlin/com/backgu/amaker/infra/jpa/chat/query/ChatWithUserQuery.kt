package com.backgu.amaker.infra.jpa.chat.query

import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.domain.chat.DefaultChatWithUser
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.infra.jpa.user.query.UserQuery
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

class ChatWithUserQuery
    @QueryProjection
    constructor(
        val id: Long = 0L,
        val chatRoomId: Long,
        val content: String,
        val chatType: ChatType,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        userId: String,
        userName: String,
        userEmail: String,
        userPicture: String,
    ) {
        fun toDomain(): DefaultChatWithUser =
            DefaultChatWithUser(
                id = id,
                chatRoomId = chatRoomId,
                content = content,
                chatType = chatType,
                createdAt = createdAt,
                updatedAt = updatedAt,
                user =
                    User.of(
                        id = user.id,
                        name = user.name,
                        email = user.email,
                        picture = user.picture,
                    ),
            )

        companion object {
            fun of(
                chat: Chat,
                user: User,
            ): ChatWithUserQuery =
                ChatWithUserQuery(
                    id = chat.id,
                    chatRoomId = chat.chatRoomId,
                    content = chat.content,
                    chatType = chat.chatType,
                    createdAt = chat.createdAt,
                    updatedAt = chat.updatedAt,
                    userId = user.id,
                    userName = user.name,
                    userEmail = user.email,
                    userPicture = user.picture,
                )
        }

        val user: UserQuery = UserQuery(id = userId, name = userName, email = userEmail, picture = userPicture)
    }
