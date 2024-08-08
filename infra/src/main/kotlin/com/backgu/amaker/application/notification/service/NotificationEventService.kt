package com.backgu.amaker.application.notification.service

import com.backgu.amaker.domain.notifiacation.Notification

interface NotificationEventService {
    fun publishNotificationEvent(notification: Notification)
}
