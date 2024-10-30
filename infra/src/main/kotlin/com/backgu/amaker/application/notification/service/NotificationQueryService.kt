package com.backgu.amaker.application.notification.service

import com.backgu.amaker.domain.notifiacation.EventNotification
import com.backgu.amaker.infra.jpa.notification.repository.EventNotificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NotificationQueryService(
    private val eventNotificationRepository: EventNotificationRepository,
) {
    fun getNotification(
        userId: String,
        workspaceId: Long,
        page: Pageable,
    ): Page<EventNotification> = eventNotificationRepository.findByUserId(userId, workspaceId, page).map { it.toDomain() }
}
