package com.backgu.amaker.infra.jpa.workspace.repository

import com.backgu.amaker.domain.workspace.WorkspaceUserStatus
import com.backgu.amaker.infra.jpa.workspace.entity.WorkspaceUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface WorkspaceUserRepository : JpaRepository<WorkspaceUserEntity, Long> {
    @Query("select wu.workspaceId from WorkspaceUser wu where wu.userId = :userId")
    fun findWorkspaceIdsByUserId(
        @Param("userId") userId: String,
    ): List<Long>

    fun existsByUserIdAndWorkspaceIdAndStatus(
        userId: String,
        workspaceId: Long,
        status: WorkspaceUserStatus,
    ): Boolean

    fun existsByUserIdAndWorkspaceId(
        userId: String,
        workspaceId: Long,
    ): Boolean

    @Query("select wu from WorkspaceUser wu where wu.userId = :userId and wu.workspaceId = :workspaceId")
    fun findByUserIdAndWorkspaceId(
        userId: String,
        workspaceId: Long,
    ): WorkspaceUserEntity?

    @Query(
        "select case when count(c)> 0 then true else false end " +
            "from Chat c " +
            "inner join ChatRoom ch on ch.id = c.chatRoomId " +
            "inner join WorkspaceUser wu on wu.workspaceId = ch.workspaceId " +
            "where wu.userId = :userId and c.id = :chatId",
    )
    fun existsByUserIdAndChatIdInWorkspace(
        userId: String,
        chatId: Long,
    ): Boolean

    @Query(
        "select wu.userId from WorkspaceUser wu " +
            "where wu.workspaceId = :workspaceId and wu.status = :status",
    )
    fun findUserIdsByWorkspaceIdAndStatus(
        workspaceId: Long,
        status: WorkspaceUserStatus,
    ): List<String>

    @Query("select wu from WorkspaceUser wu where wu.workspaceId = :workspaceId and wu.status = 'ACTIVE'")
    fun findByWorkspaceId(workspaceId: Long): List<WorkspaceUserEntity>
}
