package com.backgu.amaker.realtime.notification.dto.request

import com.backgu.amaker.realtime.notification.dto.RealTimeNotificationDto

data class IndividualUserRealTimeNotificationRequest(
    var notificationType: String,
    var userIds: List<String>,
    var title: String,
    var message: String,
) {
    fun toDto(): RealTimeNotificationDto =
        RealTimeNotificationDto(
            notificationType = notificationType,
            title = title,
            message = message,
        )
}
