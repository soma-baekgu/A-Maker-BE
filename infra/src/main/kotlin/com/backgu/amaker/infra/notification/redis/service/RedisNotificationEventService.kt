package com.backgu.amaker.infra.notification.redis.service

import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.domain.notifiacation.Notification
import org.springframework.data.redis.core.RedisTemplate

class RedisNotificationEventService(
    private val redisTemplate: RedisTemplate<String, Notification>,
) : NotificationEventService {
    override fun publishNotificationEvent(notification: Notification) {
        redisTemplate.convertAndSend("notification", notification)
    }
}
