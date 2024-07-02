package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.jpa.ChatRoomEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatRoomRepository : JpaRepository<ChatRoomEntity, Long> {
    @Query("select cr from ChatRoom cr where cr.workspaceId = :workspaceId and cr.chatRoomType = 'GROUP'")
    fun findByWorkspaceId(workspaceId: Long): List<ChatRoomEntity>
}
