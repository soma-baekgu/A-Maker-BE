package com.backgu.amaker.domain.notifiacation.method

abstract class RealTimeNotificationMethod : NotificationMethod {
    fun toPushNotificationMethod(): PushNotificationMethod = PushNotificationMethod(title, message)
}
