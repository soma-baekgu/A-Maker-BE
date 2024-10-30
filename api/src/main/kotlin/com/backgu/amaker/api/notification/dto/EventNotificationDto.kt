package com.backgu.amaker.api.notification.dto

import com.backgu.amaker.domain.notifiacation.EventNotification

data class EventNotificationDto(
    val id: Long,
    val title: String,
    val content: String,
    val userId: String,
    val eventId: Long,
) {
    companion object {
        fun from(eventNotification: EventNotification): EventNotificationDto =
            EventNotificationDto(
                id = eventNotification.id,
                title = eventNotification.title,
                content = eventNotification.content,
                userId = eventNotification.userId,
                eventId = eventNotification.eventId,
            )
    }
}
