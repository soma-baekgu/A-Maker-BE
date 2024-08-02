package com.backgu.amaker.infra.notification.rmq.serivce

import com.backgu.amaker.application.notification.event.NotificationEvent
import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.infra.rmq.config.RMQConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate

class NotificationRMQEventService(
    private val rabbitTemplate: RabbitTemplate,
) : NotificationEventService {
    override fun publishNotificationEvent(notificationEvent: NotificationEvent) {
        rabbitTemplate.convertAndSend(RMQConfig.NOTIFICATION_QUEUE, notificationEvent)
    }
}
