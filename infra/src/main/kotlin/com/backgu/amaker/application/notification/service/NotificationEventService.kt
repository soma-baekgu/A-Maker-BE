package com.backgu.amaker.application.notification.service

import com.backgu.amaker.application.notification.event.NotificationEvent

interface NotificationEventService {
    fun publishNotificationEvent(notificationEvent: NotificationEvent)
}
