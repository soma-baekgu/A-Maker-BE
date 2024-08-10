package com.backgu.amaker.notification.realtime.infra.dto

import com.backgu.amaker.domain.notifiacation.RealTimeBasedNotification

data class IndividualUserRealTimeNotificationRequest(
    var type: String,
    var userIds: List<String>,
    var title: String,
    var message: String,
) {
    companion object {
        fun of(
            userIds: List<String>,
            notification: RealTimeBasedNotification,
        ): IndividualUserRealTimeNotificationRequest =
            IndividualUserRealTimeNotificationRequest(
                type = notification.javaClass.simpleName,
                userIds = userIds,
                title = notification.method.title,
                message = notification.method.message,
            )
    }
}
