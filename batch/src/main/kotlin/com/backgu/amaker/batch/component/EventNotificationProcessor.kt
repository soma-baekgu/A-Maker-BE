package com.backgu.amaker.batch.component

import com.backgu.amaker.batch.dto.NotificationWithEntityDto
import com.backgu.amaker.batch.query.EventNotificationQuery
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.domain.notifiacation.event.EventNotCompleted
import com.backgu.amaker.domain.notifiacation.event.EventOverdue
import com.backgu.amaker.infra.jpa.notification.entity.EventNotificationEntity
import org.springframework.batch.item.ItemProcessor
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime

@Component
class EventNotificationProcessor {
    @Bean
    fun processor(): ItemProcessor<EventNotificationQuery, NotificationWithEntityDto> =
        ItemProcessor { item ->
            val now = LocalDateTime.now()

            if (item.isAfter(now)) {
                return@ItemProcessor null
            }

            if (!item.shouldSendNotification(now)) {
                return@ItemProcessor null
            }

            val notification: Notification =
                if (item.deadline.isBefore(now)) {
                    EventOverdue.of(item.userId, Duration.between(item.deadline, now))
                } else {
                    EventNotCompleted.of(item.userId, Duration.between(now, item.deadline))
                }

            val eventNotificationEntity =
                EventNotificationEntity(
                    title = notification.method.title,
                    content = notification.method.message,
                    userId = item.userId,
                    eventId = item.eventId,
                )

            NotificationWithEntityDto.of(notification, eventNotificationEntity)
        }
}
