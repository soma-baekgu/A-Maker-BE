package com.backgu.amaker.application.notification.service

import com.backgu.amaker.application.notification.event.NotificationEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

interface NotificationService {
    @Async
    @EventListener
    fun handleNotificationEvent(notificationEvent: NotificationEvent)
}
