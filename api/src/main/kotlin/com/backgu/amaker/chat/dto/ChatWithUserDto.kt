package com.backgu.amaker.chat.dto

import com.backgu.amaker.chat.domain.Chat
import com.backgu.amaker.chat.domain.ChatType
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.dto.UserDto
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

class ChatWithUserDto
    @QueryProjection
    constructor(
        override val id: Long = 0L,
        override val chatRoomId: Long,
        override val content: String,
        override val chatType: ChatType,
        override val createdAt: LocalDateTime,
        override val updatedAt: LocalDateTime,
        userId: String,
        userName: String,
        userEmail: String,
        userPicture: String,
    ) : ChatWithUser<String> {
        override val user: UserDto = UserDto(id = userId, name = userName, email = userEmail, picture = userPicture)

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
        }
    }
