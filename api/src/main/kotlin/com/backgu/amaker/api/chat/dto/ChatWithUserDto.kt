package com.backgu.amaker.api.chat.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.chat.ChatType
import java.time.LocalDateTime

interface ChatWithUserDto<T> {
    val id: Long
    val chatRoomId: Long
    val content: T
    val chatType: ChatType
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
    val user: UserDto
}
