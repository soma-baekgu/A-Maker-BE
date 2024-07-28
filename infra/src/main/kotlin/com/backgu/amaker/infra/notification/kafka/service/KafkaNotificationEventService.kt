package com.backgu.amaker.infra.notification.kafka.service

import com.backgu.amaker.application.notification.event.NotificationEvent
import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.infra.kafka.config.KafkaConfig
import org.springframework.kafka.core.KafkaTemplate

class KafkaNotificationEventService(
    private val kafkaTemplate: KafkaTemplate<String, NotificationEvent>,
) : NotificationEventService {
    override fun publishNotificationEvent(notificationEvent: NotificationEvent) {
        kafkaTemplate.send(KafkaConfig.NOTIFICATION_TOPIC, notificationEvent)
    }
}
