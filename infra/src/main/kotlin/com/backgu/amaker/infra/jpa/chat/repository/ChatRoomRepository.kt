package com.backgu.amaker.infra.jpa.chat.repository

import com.backgu.amaker.domain.chat.ChatRoomType
import com.backgu.amaker.infra.jpa.chat.entity.ChatRoomEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatRoomRepository : JpaRepository<ChatRoomEntity, Long> {
    fun findByWorkspaceIdAndChatRoomType(
        workspaceId: Long,
        chatRoomType: ChatRoomType,
    ): ChatRoomEntity?

    fun findByIdIn(chatRoomIds: List<Long>): List<ChatRoomEntity>

    fun findByWorkspaceId(workspaceId: Long): List<ChatRoomEntity>

    @Query(
        "select cr from ChatRoom cr " +
            "where cr.workspaceId = :workspaceId " +
            "and cr.id not in (" +
            "select cru.chatRoomId from ChatRoomUser cru where cru.userId = :userId" +
            ")",
    )
    fun findByWorkspaceIdWithNotRegisteredUser(
        workspaceId: Long,
        userId: String,
    ): List<ChatRoomEntity>

    fun findByIdInAndWorkspaceId(
        chatRoomIds: List<Long>,
        workspaceId: Long,
    ): List<ChatRoomEntity>

    fun findByIdAndWorkspaceId(
        id: Long,
        workspaceId: Long,
    ): ChatRoomEntity?
}
