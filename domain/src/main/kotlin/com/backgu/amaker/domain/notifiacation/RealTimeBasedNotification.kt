package com.backgu.amaker.domain.notifiacation

import com.backgu.amaker.domain.notifiacation.method.RealTimeNotificationMethod
import com.backgu.amaker.domain.user.UserDevice

abstract class RealTimeBasedNotification(
    override val method: RealTimeNotificationMethod,
) : Notification {
    fun toPushNotification(userDevices: List<UserDevice>): PushNotification =
        PushNotification(method.toPushNotificationMethod(), userDevices)
}
