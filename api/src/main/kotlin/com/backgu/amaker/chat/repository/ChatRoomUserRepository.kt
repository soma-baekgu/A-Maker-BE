package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.jpa.ChatRoomUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatRoomUserRepository : JpaRepository<ChatRoomUserEntity, Long> {
    fun existsByUserIdAndChatRoomId(
        userId: String,
        chatRoomId: Long,
    ): Boolean

    @Query("select c from ChatRoomUser c where c.userId = :userId and c.chatRoomId = :chatRoomId")
    fun findByUserIdAndChatRoomId(
        userId: String,
        chatRoomId: Long,
    ): ChatRoomUserEntity?
}
