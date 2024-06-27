package com.backgu.amaker.workspace.repository

import com.backgu.amaker.workspace.domain.Workspace
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface WorkspaceRepository : JpaRepository<Workspace, Long> {
    @Query("select w from Workspace w where w.id in :workspaceIds")
    fun findByWorkspaceIds(
        @Param("workspaceIds") workspaceIds: List<Long>,
    ): List<Workspace>
}
