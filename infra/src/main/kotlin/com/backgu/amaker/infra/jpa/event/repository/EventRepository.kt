package com.backgu.amaker.infra.jpa.event.repository

import com.backgu.amaker.infra.jpa.event.entity.EventEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EventRepository : JpaRepository<EventEntity, Long> {
    @Query(
        "SELECT e FROM Event e " +
            "inner join Chat c on e.id = c.id " +
            "inner join ChatRoom ch on ch.id = c.chatRoomId " +
            "WHERE ch.workspaceId = :workspaceId",
    )
    fun findAllByWorkspaceId(workspaceId: Long): List<EventEntity>
}
