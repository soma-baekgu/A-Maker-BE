package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.jpa.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatRepository : JpaRepository<ChatEntity, String> {
    @Query(
        "select c from Chat c where c.chatRoomId = :chatRoomId and c.id < :cursor order by c.id desc limit :size",
    )
    fun findTopByChatRoomIdLittleThanCursorLimitCount(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatEntity>
}
