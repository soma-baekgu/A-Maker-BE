package com.backgu.amaker.infra.jpa.chat.repository

import com.backgu.amaker.infra.jpa.chat.entity.ChatRoomUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

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

    fun findByChatRoomIdIn(userIds: List<Long>): List<ChatRoomUserEntity>

    fun findByUserIdInAndChatRoomId(
        userIds: List<String>,
        chatRoomId: Long,
    ): List<ChatRoomUserEntity>

    fun findAllByChatRoomId(chatRoomId: Long): List<ChatRoomUserEntity>

    @Query("select cru.userId from ChatRoomUser cru where cru.chatRoomId = :chatRoomId")
    fun findUserIdsByChatRoomId(chatRoomId: Long): List<String>
}
