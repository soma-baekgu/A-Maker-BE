package com.backgu.amaker.domain.chat

import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

sealed interface ChatWithUser<T> {
    val id: Long
    val chatRoomId: Long
    val content: T
    val chatType: ChatType
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
    val user: User
}
