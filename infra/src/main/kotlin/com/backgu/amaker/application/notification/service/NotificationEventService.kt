package com.backgu.amaker.application.notification.service

import com.backgu.amaker.domain.notifiacation.Notification
import org.springframework.transaction.event.TransactionalEventListener

interface NotificationEventService {
    @TransactionalEventListener
    fun publishNotificationEvent(notification: Notification)
}
