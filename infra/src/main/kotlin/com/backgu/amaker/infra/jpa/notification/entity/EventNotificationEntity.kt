package com.backgu.amaker.infra.jpa.notification.entity

import com.backgu.amaker.domain.notifiacation.EventNotification
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity(name = "EventNotification")
@Table(name = "event_notification")
@DiscriminatorValue(value = "EVENT")
class EventNotificationEntity(
    title: String,
    content: String,
    userId: String,
    @Column(nullable = false)
    val eventId: Long,
) : NotificationEntity(title = title, content = content, userId = userId) {
    fun toDomain(): EventNotification =
        EventNotification(
            id = id,
            title = title,
            content = content,
            userId = userId,
            eventId = eventId,
        )
}
