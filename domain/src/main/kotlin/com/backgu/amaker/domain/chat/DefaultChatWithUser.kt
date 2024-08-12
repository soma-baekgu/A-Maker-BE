package com.backgu.amaker.domain.chat

import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

class DefaultChatWithUser(
    override val id: Long = 0L,
    override val chatRoomId: Long,
    override val content: String,
    override val chatType: ChatType,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    override val user: User,
) : ChatWithUser<String>
