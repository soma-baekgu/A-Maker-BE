package com.backgu.amaker.api.chat.dto

import com.backgu.amaker.api.event.dto.EventWithUSerDto
import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.chat.ChatType
import java.time.LocalDateTime

class EventChatWithUserDto(
    override val id: Long,
    override val chatRoomId: Long,
    override val content: EventWithUSerDto,
    override val chatType: ChatType,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    override val user: UserDto,
) : ChatWithUserDto<EventWithUSerDto> {
    companion object {
        fun of(
            chat: ChatWithUserDto<*>,
            eventDto: EventWithUSerDto,
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
