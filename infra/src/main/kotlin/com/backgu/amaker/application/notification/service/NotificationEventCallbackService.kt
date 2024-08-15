package com.backgu.amaker.application.notification.service

import com.backgu.amaker.domain.notifiacation.Notification

interface NotificationEventCallbackService<T> : NotificationEventService {
    override fun publishNotificationEvent(notification: Notification) {
        try {
            val res = process(notification)
            onSuccess(res)
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    fun process(notification: Notification): T

    fun onSuccess(param: T)

    fun onFailure(exception: Exception)
}
