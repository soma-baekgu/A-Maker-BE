package com.backgu.amaker.application.notification.service

import com.backgu.amaker.application.notification.event.NotificationEvent
import com.backgu.amaker.application.notification.event.NotificationEventWithCallback
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

private val logger = KotlinLogging.logger {}

interface NotificationWithCallBackService {
    @Async
    @EventListener
    fun handleNotificationWithCallback(notificationEvent: NotificationEventWithCallback) {
        try {
            notificationEvent.preHandle()
            handleNotificationEvent(notificationEvent.notificationEvent)
            notificationEvent.postHandle()
        } catch (e: Exception) {
            notificationEvent.onFail(e)
            onHandlerFailed(notificationEvent.notificationEvent, e)
        }
    }

    fun handleNotificationEvent(notificationEvent: NotificationEvent)

    fun onHandlerFailed(
        notificationEvent: NotificationEvent,
        exception: Exception,
    ) {
        logger.error { "notificationEvent = [$notificationEvent], exception = [$exception]" }
    }
}
