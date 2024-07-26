package com.backgu.amaker.infra.jpa.chat.repository

import com.backgu.amaker.infra.jpa.chat.entity.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatJpaRepository : JpaRepository<ChatEntity, Long> {
    fun countByChatRoomId(chatRoomId: Long): Long

    @Query("select count(c) from Chat c where c.chatRoomId = :chatRoomId and c.id > :lastReadChatId")
    fun countByChatRoomIdAndLastReadChatIdGreaterThan(
        chatRoomId: Long,
        lastReadChatId: Long,
    ): Long
}
