package com.backgu.amaker.notification.service

import com.backgu.amaker.application.notification.service.NotificationWithCallBackService
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.notification.service.adapter.NotificationHandlerAdapter
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val notificationHandlerAdapters: List<NotificationHandlerAdapter<*, *>>,
) : NotificationWithCallBackService {
    override fun handleNotificationEvent(notification: Notification) {
        notificationHandlerAdapters.forEach { it.handle(notification) }
    }
}
