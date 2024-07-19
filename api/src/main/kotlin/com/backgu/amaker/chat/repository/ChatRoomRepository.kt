package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.chat.jpa.ChatRoomEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository : JpaRepository<ChatRoomEntity, Long> {
    fun findByWorkspaceIdAndChatRoomType(
        workspaceId: Long,
        chatRoomType: ChatRoomType,
    ): ChatRoomEntity?

    fun findByIdIn(chatRoomIds: List<Long>): List<ChatRoomEntity>
}
