package com.backgu.amaker.workspace.repository

import com.backgu.amaker.workspace.domain.Workspace
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface WorkspaceRepository : JpaRepository<Workspace, Long> {
    @Query("select w from Workspace w where w.id in :workspaceIds")
    fun findByWorkspaceIds(
        @Param("workspaceIds") workspaceIds: List<Long>,
    ): List<Workspace>

    @Query(
        "select w " +
            "from Workspace w " +
            "join WorkspaceUser wu on w.id = wu.workspaceId " +
            "where wu.userId = :userId " +
            "order by wu.id desc limit 1",
    )
    fun getDefaultWorkspaceByUserId(userId: UUID): Workspace?
}
