package com.backgu.amaker.api.chat.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.domain.chat.DefaultChatWithUser
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

class DefaultChatWithUserDto(
    override val id: Long = 0L,
    override val chatRoomId: Long,
    override val content: String,
    override val chatType: ChatType,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    override val user: UserDto,
) : ChatWithUserDto<String> {
    companion object {
        fun of(
            chat: Chat,
            user: User,
        ): DefaultChatWithUserDto =
            DefaultChatWithUserDto(
                id = chat.id,
                chatRoomId = chat.chatRoomId,
                content = chat.content,
                chatType = chat.chatType,
                createdAt = chat.createdAt,
                updatedAt = chat.updatedAt,
                user = UserDto(id = user.id, name = user.name, email = user.email, picture = user.picture),
            )

        fun of(chatWithUser: DefaultChatWithUser) =
            DefaultChatWithUserDto(
                id = chatWithUser.id,
                chatRoomId = chatWithUser.chatRoomId,
                content = chatWithUser.content,
                chatType = chatWithUser.chatType,
                createdAt = chatWithUser.createdAt,
                updatedAt = chatWithUser.updatedAt,
                user =
                    UserDto(
                        id = chatWithUser.user.id,
                        name = chatWithUser.user.name,
                        email = chatWithUser.user.email,
                        picture = chatWithUser.user.picture,
                    ),
            )

        fun of(
            defaultChatWithUser: DefaultChatWithUser,
            user: User,
        ): DefaultChatWithUserDto =
            DefaultChatWithUserDto(
                id = defaultChatWithUser.id,
                chatRoomId = defaultChatWithUser.chatRoomId,
                content = defaultChatWithUser.content,
                chatType = defaultChatWithUser.chatType,
                createdAt = defaultChatWithUser.createdAt,
                updatedAt = defaultChatWithUser.updatedAt,
                user = UserDto(id = user.id, name = user.name, email = user.email, picture = user.picture),
            )
    }
}
