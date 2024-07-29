package com.backgu.amaker.notification.service

import com.backgu.amaker.application.notification.event.NotificationEvent
import com.backgu.amaker.application.notification.mail.event.EmailEvent
import com.backgu.amaker.application.notification.service.NotificationService
import com.backgu.amaker.infra.mail.service.EmailHandler
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val emailHandler: EmailHandler,
) : NotificationService {
    override fun handleNotificationEvent(notificationEvent: NotificationEvent) {
        when (notificationEvent) {
            is EmailEvent -> emailHandler.handleEmailEvent(notificationEvent)
        }
    }
}
