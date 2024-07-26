package com.backgu.amaker.chat.dto

import com.backgu.amaker.chat.domain.ChatType
import com.backgu.amaker.event.dto.EventDtoWithUser
import com.backgu.amaker.user.dto.UserDto
import java.time.LocalDateTime

class EventChatWithUserDto(
    override val id: Long,
    override val chatRoomId: Long,
    override val content: EventDtoWithUser,
    override val chatType: ChatType,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    override val user: UserDto,
) : ChatWithUser<EventDtoWithUser> {
    companion object {
        fun of(
            chat: ChatWithUser<*>,
            eventDto: EventDtoWithUser,
        ): EventChatWithUserDto =
            EventChatWithUserDto(
                id = chat.id,
                chatRoomId = chat.chatRoomId,
                content = eventDto,
                chatType = chat.chatType,
                createdAt = chat.createdAt,
                updatedAt = chat.updatedAt,
                user = chat.user,
            )
    }
}
