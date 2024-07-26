package com.backgu.amaker.api.chat.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.infra.jpa.chat.query.ChatWithUserQuery
import java.time.LocalDateTime

class ChatWithUserDto(
    override val id: Long = 0L,
    override val chatRoomId: Long,
    override val content: String,
    override val chatType: ChatType,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    override val user: UserDto,
) : ChatWithUser<String> {
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
                user = UserDto(id = user.id, name = user.name, email = user.email, picture = user.picture),
            )

        fun of(chatWithUserQuery: ChatWithUserQuery) =
            ChatWithUserDto(
                id = chatWithUserQuery.id,
                chatRoomId = chatWithUserQuery.chatRoomId,
                content = chatWithUserQuery.content,
                chatType = chatWithUserQuery.chatType,
                createdAt = chatWithUserQuery.createdAt,
                updatedAt = chatWithUserQuery.updatedAt,
                user =
                    UserDto(
                        id = chatWithUserQuery.user.id,
                        name = chatWithUserQuery.user.name,
                        email = chatWithUserQuery.user.email,
                        picture = chatWithUserQuery.user.picture,
                    ),
            )
    }
}
