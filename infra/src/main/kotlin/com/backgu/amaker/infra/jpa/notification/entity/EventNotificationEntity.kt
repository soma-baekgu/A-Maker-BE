package com.backgu.amaker.infra.jpa.notification.entity

import com.backgu.amaker.domain.notifiacation.EventNotificationType
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val eventNotificationType: EventNotificationType,
) : NotificationEntity(title = title, content = content, userId = userId)
