package com.backgu.amaker.infra.notification.redis.service

import com.backgu.amaker.domain.notifiacation.Notification
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener

class RedisEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher,
) : MessageListener {
    private val objectMapper = ObjectMapper()

    override fun onMessage(
        message: Message,
        pattern: ByteArray?,
    ) {
        val notificationEvent =
            objectMapper.readValue(String(message.body), Notification::class.java)
        applicationEventPublisher.publishEvent(notificationEvent)
    }
}
