package com.backgu.amaker.infra.notification.redis.service

import com.backgu.amaker.application.notification.event.NotificationEvent
import com.backgu.amaker.application.notification.service.NotificationEventService
import org.springframework.data.redis.core.RedisTemplate

class RedisNotificationEventService(
    private val redisTemplate: RedisTemplate<String, NotificationEvent>,
) : NotificationEventService {
    override fun publishNotificationEvent(notificationEvent: NotificationEvent) {
        redisTemplate.convertAndSend("notification", notificationEvent)
    }
}
