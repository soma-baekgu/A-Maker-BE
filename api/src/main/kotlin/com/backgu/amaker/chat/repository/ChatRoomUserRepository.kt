package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.jpa.ChatRoomUserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomUserRepository : JpaRepository<ChatRoomUserEntity, Long> {
    fun existsByUserIdAndChatRoomId(
        userId: String,
        chatRoomId: Long,
    ): Boolean

    fun findByUserId(userId: String): List<ChatRoomUserEntity>

    fun findByUserIdAndChatRoomId(
        userId: String,
        chatRoomId: Long,
    ): ChatRoomUserEntity?

    fun findByChatRoomIdIn(chatRoomIds: List<Long>): List<ChatRoomUserEntity>
}
