package com.backgu.amaker.infra.jpa.notification.repository

import com.backgu.amaker.infra.jpa.notification.entity.EventNotificationEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EventNotificationRepository : JpaRepository<EventNotificationEntity, Long> {
    @Query(
        """
        SELECT en
        FROM EventNotification en
        INNER JOIN fetch Event e ON e.id = en.eventId
        INNER JOIN fetch Chat ch ON ch.id = e.id
        INNER JOIN fetch ChatRoom cr ON cr.id = ch.chatRoomId
        WHERE en.userId = :userId
        AND cr.workspaceId = :workspaceId
        order by en.id desc
        """,
    )
    fun findByUserId(
        userId: String,
        workspaceId: Long,
        pageable: Pageable,
    ): Page<EventNotificationEntity>
}
