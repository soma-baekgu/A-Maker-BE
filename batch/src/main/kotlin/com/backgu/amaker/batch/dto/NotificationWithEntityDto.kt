package com.backgu.amaker.batch.dto

import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.infra.jpa.notification.entity.EventNotificationEntity

data class NotificationWithEntityDto(
    val notification: Notification,
    val eventNotificationEntity: EventNotificationEntity,
) {
    companion object {
        fun of(
            notification: Notification,
            eventNotificationEntity: EventNotificationEntity,
        ): NotificationWithEntityDto =
            NotificationWithEntityDto(
                notification,
                eventNotificationEntity,
            )
    }
}
