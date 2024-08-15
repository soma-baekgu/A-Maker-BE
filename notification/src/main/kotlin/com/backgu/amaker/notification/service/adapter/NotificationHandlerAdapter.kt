package com.backgu.amaker.notification.service.adapter

import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.domain.notifiacation.method.NotificationMethod

interface NotificationHandlerAdapter<N : Notification, M : NotificationMethod> {
    fun supportsNotification(notification: Notification): Boolean

    fun supportsMethod(method: NotificationMethod): Boolean

    @Suppress("UNCHECKED_CAST")
    fun handle(notification: Notification) {
        if (supportsNotification(notification) && supportsMethod(notification.method)) {
            process(
                notification as N,
                notification.method as M,
            )
        }
    }

    fun process(
        notification: N,
        method: M,
    )
}
