package com.backgu.amaker.notification.push.fcm.service

import com.backgu.amaker.domain.notifiacation.PushNotification
import com.backgu.amaker.domain.user.UserDevice
import org.springframework.stereotype.Service

@Service
class FCMService(
    private val fcmCallService: FCMCallService,
) {
    fun sendMessage(
        userDevice: UserDevice,
        notification: PushNotification,
    ) {
        fcmCallService.sendMessage(
            userDevice.token,
            notification.method.title,
            notification.method.message,
        )
    }
}
