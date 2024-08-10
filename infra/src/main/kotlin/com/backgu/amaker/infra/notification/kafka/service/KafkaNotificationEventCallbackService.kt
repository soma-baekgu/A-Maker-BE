package com.backgu.amaker.infra.notification.kafka.service

import com.backgu.amaker.application.notification.service.NotificationEventCallbackService
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.infra.kafka.config.KafkaConfig
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import java.util.concurrent.CompletableFuture

private val logger: KLogger = KotlinLogging.logger {}

class KafkaNotificationEventCallbackService(
    private val kafkaTemplate: KafkaTemplate<String, Notification>,
) : NotificationEventCallbackService<CompletableFuture<*>> {
    override fun process(notification: Notification): CompletableFuture<SendResult<String, Notification>> =
        kafkaTemplate.send(KafkaConfig.NOTIFICATION_TOPIC, notification)

    override fun onSuccess(param: CompletableFuture<*>) {
        param.join()
    }

    override fun onFailure(exception: Exception) {
        logger.error { "Failed to send notification event to Kafka: ${exception.message}" }
    }
}
