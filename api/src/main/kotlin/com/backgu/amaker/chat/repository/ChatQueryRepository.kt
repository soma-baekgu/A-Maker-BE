package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.dto.ChatDto

interface ChatQueryRepository {
    fun findTopByChatRoomIdLittleThanCursorLimitCountWithUser(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatDto>

    fun findTopByChatRoomIdGreaterThanCursorLimitCountWithUser(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatDto>
}
