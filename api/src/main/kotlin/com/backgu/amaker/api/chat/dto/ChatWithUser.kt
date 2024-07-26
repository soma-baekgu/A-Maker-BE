package com.backgu.amaker.chat.dto

import com.backgu.amaker.chat.domain.ChatType
import com.backgu.amaker.user.dto.UserDto
import java.time.LocalDateTime

interface ChatWithUser<T> {
    val id: Long
    val chatRoomId: Long
    val content: T
    val chatType: ChatType
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
    val user: UserDto
}
