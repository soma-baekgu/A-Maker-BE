package com.backgu.amaker.batch.component

import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.batch.dto.NotificationWithEntityDto
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class NotificationPublisherWriter(
    private val notificationEventService: NotificationEventService,
) : ItemWriter<NotificationWithEntityDto> {
    override fun write(it: Chunk<out NotificationWithEntityDto>) {
        it.forEach { item ->
            notificationEventService.publishNotificationEvent(item.notification)
        }
    }
}
