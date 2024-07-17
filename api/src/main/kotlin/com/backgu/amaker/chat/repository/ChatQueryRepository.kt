package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.dto.ChatWithUserDto

interface ChatQueryRepository {
    fun findTopByChatRoomIdLittleThanCursorLimitCountWithUser(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatWithUserDto>

    fun findTopByChatRoomIdGreaterThanCursorLimitCountWithUser(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatWithUserDto>

    fun findByIdWithUser(chatId: Long): ChatWithUserDto?
}
