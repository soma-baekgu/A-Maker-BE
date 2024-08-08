package com.backgu.amaker.application.notification.service

import com.backgu.amaker.application.notification.event.NotificationEventWithCallback
import com.backgu.amaker.domain.notifiacation.Notification
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

private val logger = KotlinLogging.logger {}

interface NotificationWithCallBackService : NotificationService {
    @Async
    @EventListener
    fun handleNotificationWithCallback(notificationEvent: NotificationEventWithCallback) {
        try {
            notificationEvent.preHandle()
            handleNotificationEvent(notificationEvent.notification)
            notificationEvent.postHandle()
        } catch (e: Exception) {
            notificationEvent.onFail(e)
            onHandlerFailed(notificationEvent.notification, e)
        }
    }

    override fun handleNotificationEvent(notification: Notification)

    fun onHandlerFailed(
        notification: Notification,
        exception: Exception,
    ) {
        logger.error { "notificationEvent = [$notification], exception = [$exception]" }
    }
}
