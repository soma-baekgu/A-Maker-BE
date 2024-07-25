package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.query.ChatWithUserQuery

interface ChatQueryRepository {
    fun findTopByChatRoomIdLittleThanCursorLimitCountWithUser(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatWithUserQuery>

    fun findTopByChatRoomIdGreaterThanCursorLimitCountWithUser(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatWithUserQuery>

    fun findByIdWithUser(chatId: Long): ChatWithUserQuery?
}
