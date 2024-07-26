package com.backgu.amaker.infra.jpa.chat.repository

import com.backgu.amaker.infra.jpa.chat.query.ChatWithUserQuery

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
