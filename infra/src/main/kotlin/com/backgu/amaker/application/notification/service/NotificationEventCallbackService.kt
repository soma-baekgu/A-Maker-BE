package com.backgu.amaker.application.notification.service

import com.backgu.amaker.application.notification.event.NotificationEvent

interface NotificationEventCallbackService<T> : NotificationEventService {
    override fun publishNotificationEvent(notificationEvent: NotificationEvent) {
        try {
            val res = process(notificationEvent)
            onSuccess(res)
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    fun process(notificationEvent: NotificationEvent): T

    fun onSuccess(param: T)

    fun onFailure(exception: Exception)
}
