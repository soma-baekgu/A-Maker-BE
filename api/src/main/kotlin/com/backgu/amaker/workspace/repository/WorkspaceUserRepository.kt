package com.backgu.amaker.workspace.repository

import com.backgu.amaker.workspace.jpa.WorkspaceUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface WorkspaceUserRepository : JpaRepository<WorkspaceUserEntity, Long> {
    @Query("select wu.workspaceId from WorkspaceUser wu where wu.userId = :userId")
    fun findWorkspaceIdsByUserId(
        @Param("userId") userId: UUID,
    ): List<Long>
}
