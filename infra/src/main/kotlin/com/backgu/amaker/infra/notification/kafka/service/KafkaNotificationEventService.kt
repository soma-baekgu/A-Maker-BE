package com.backgu.amaker.infra.notification.kafka.service

import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.infra.kafka.config.KafkaConfig
import org.springframework.kafka.core.KafkaTemplate

class KafkaNotificationEventService(
    private val kafkaTemplate: KafkaTemplate<String, Notification>,
) : NotificationEventService {
    override fun publishNotificationEvent(notification: Notification) {
        kafkaTemplate.send(KafkaConfig.NOTIFICATION_TOPIC, notification.getNotificationKey(), notification)
    }
}
