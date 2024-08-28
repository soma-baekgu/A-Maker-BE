package com.backgu.amaker.infra.jpa.workspace.repository

import com.backgu.amaker.infra.jpa.workspace.entity.WorkspaceEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface WorkspaceRepository : JpaRepository<WorkspaceEntity, Long> {
    @Query("select w from Workspace w where w.id in :workspaceIds")
    fun findByWorkspaceIds(
        @Param("workspaceIds") workspaceIds: List<Long>,
    ): List<WorkspaceEntity>

    @Query(
        "select w " +
            "from Workspace w " +
            "join WorkspaceUser wu on w.id = wu.workspaceId " +
            "where wu.userId = :userId " +
            "order by wu.id desc limit 1",
    )
    fun getDefaultWorkspaceByUserId(userId: String): WorkspaceEntity?

    @Modifying
    @Query(
        "update Workspace w " +
            "set w.belongingNumber = w.belongingNumber + 1 " +
            "where w.id = :workspaceId and w.belongingNumber < :limit",
    )
    fun updateBelongingWithLimit(
        workspaceId: Long,
        limit: Int,
    ): Int

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select w from Workspace w where w.id = :id")
    fun getLockedWorkspaceById(id: Long): WorkspaceEntity?

    @Query("select ch.workspaceId from ChatRoom ch where ch.id = :chatRoomId")
    fun getWorkspaceIdByChatRoomId(chatRoomId: Long): Long?
}
