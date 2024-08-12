package com.backgu.amaker.notification.service.adapter

import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.domain.notifiacation.PushNotification
import com.backgu.amaker.domain.notifiacation.method.NotificationMethod
import com.backgu.amaker.domain.notifiacation.method.PushNotificationMethod
import com.backgu.amaker.notification.push.fcm.service.FCMService
import org.springframework.stereotype.Component

@Component
class PushHandlerAdapter(
    private val fcmService: FCMService,
) : NotificationHandlerAdapter<PushNotification, PushNotificationMethod> {
    override fun supportsNotification(notification: Notification): Boolean = notification is PushNotification

    override fun supportsMethod(method: NotificationMethod): Boolean = method is PushNotificationMethod

    override fun process(
        notification: PushNotification,
        method: PushNotificationMethod,
    ) {
        notification.userDevices.forEach {
            fcmService.sendMessage(
                it,
                notification,
            )
        }
    }
}
