package com.backgu.amaker.domain.chat

import com.backgu.amaker.domain.event.EventWithUser
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

class EventChatWithUser(
    override val id: Long,
    override val chatRoomId: Long,
    override val content: EventWithUser,
    override val chatType: ChatType,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    override val user: User,
) : ChatWithUser<EventWithUser>
