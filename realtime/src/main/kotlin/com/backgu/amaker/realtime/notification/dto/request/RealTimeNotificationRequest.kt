package com.backgu.amaker.realtime.notification.dto.request

import com.backgu.amaker.realtime.notification.dto.RealTimeNotificationDto

class RealTimeNotificationRequest(
    var type: String,
    var title: String,
    var message: String,
) {
    fun toDto(): RealTimeNotificationDto =
        RealTimeNotificationDto(
            notificationType = type,
            title = title,
            message = message,
        )
}
