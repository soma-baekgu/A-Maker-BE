package com.backgu.amaker.workspace.repository

import com.backgu.amaker.workspace.domain.WorkspaceUserStatus
import com.backgu.amaker.workspace.jpa.WorkspaceUserEntity
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

    @Query("select wu from WorkspaceUser wu where wu.userId = :userId and wu.workspaceId = :workspaceId")
    fun findByUserIdAndWorkspaceId(
        userId: String,
        workspaceId: Long,
    ): WorkspaceUserEntity?
}
