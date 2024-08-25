package com.backgu.amaker.api.chat.dto

import com.backgu.amaker.api.event.dto.EventWithUserDto
import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.domain.chat.ChatWithUser
import com.backgu.amaker.domain.chat.DefaultChatWithUser
import com.backgu.amaker.domain.chat.EventChatWithUser
import com.backgu.amaker.domain.event.EventWithUser
import java.time.LocalDateTime

sealed interface ChatWithUserDto<T> {
    val id: Long
    val chatRoomId: Long
    val content: T
    val chatType: ChatType
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
    val user: UserDto

    companion object {
        fun <T> of(chatWithUser: ChatWithUser<T>): ChatWithUserDto<out Any> =
            when (chatWithUser) {
                is DefaultChatWithUser ->
                    DefaultChatWithUserDto(
                        id = chatWithUser.id,
                        chatRoomId = chatWithUser.chatRoomId,
                        content = chatWithUser.content,
                        chatType = chatWithUser.chatType,
                        createdAt = chatWithUser.createdAt,
                        updatedAt = chatWithUser.updatedAt,
                        user = UserDto.of(chatWithUser.user),
                    )

                is EventChatWithUser ->
                    EventChatWithUserDto(
                        id = chatWithUser.id,
                        chatRoomId = chatWithUser.chatRoomId,
                        content = EventWithUserDto.of(chatWithUser.content),
                        chatType = chatWithUser.chatType,
                        createdAt = chatWithUser.createdAt,
                        updatedAt = chatWithUser.updatedAt,
                        user = UserDto.of(chatWithUser.user),
                    )
            }
    }
}
