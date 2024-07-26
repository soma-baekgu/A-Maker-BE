package com.backgu.amaker.api.chat.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.infra.jpa.chat.query.ChatWithUserQuery
import java.time.LocalDateTime

class ChatWithUserDto(
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
    companion object {
        fun of(
            chat: Chat,
            user: User,
        ): ChatWithUserDto =
            ChatWithUserDto(
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

        fun of(chatWithUserQuery: ChatWithUserQuery) =
            ChatWithUserDto(
                id = chatWithUserQuery.id,
                chatRoomId = chatWithUserQuery.chatRoomId,
                content = chatWithUserQuery.content,
                chatType = chatWithUserQuery.chatType,
                createdAt = chatWithUserQuery.createdAt,
                updatedAt = chatWithUserQuery.updatedAt,
                userId = chatWithUserQuery.user.id,
                userName = chatWithUserQuery.user.name,
                userEmail = chatWithUserQuery.user.email,
                userPicture = chatWithUserQuery.user.picture,
            )
    }

    val user: UserDto = UserDto(id = userId, name = userName, email = userEmail, picture = userPicture)
}
