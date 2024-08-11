package com.backgu.amaker.batch.component

import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.batch.dto.NotificationWithEntityDto
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class NotificationPublisherWriter(
    private val notificationEventService: NotificationEventService,
) {
    @Bean
    fun pubWriter(): ItemWriter<NotificationWithEntityDto> =
        ItemWriter {
            it.forEach { item ->
                notificationEventService.publishNotificationEvent(item.notification)
            }
        }
}
