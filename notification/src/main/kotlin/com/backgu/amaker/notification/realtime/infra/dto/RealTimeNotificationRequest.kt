package com.backgu.amaker.notification.realtime.infra.dto

import com.backgu.amaker.domain.notifiacation.RealTimeBasedNotification

class RealTimeNotificationRequest(
    var type: String,
    var title: String,
    var message: String,
) {
    companion object {
        fun of(notification: RealTimeBasedNotification): RealTimeNotificationRequest =
            RealTimeNotificationRequest(
                type = notification.javaClass.simpleName,
                title = notification.method.title,
                message = notification.method.message,
            )
    }
}
