package com.backgu.amaker.infra.notification.kafka.service

import com.backgu.amaker.application.notification.event.NotificationEvent
import com.backgu.amaker.application.notification.service.NotificationEventCallbackService
import com.backgu.amaker.infra.kafka.config.KafkaConfig
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import java.util.concurrent.CompletableFuture

private val logger: KLogger = KotlinLogging.logger {}

class KafkaNotificationEventCallbackService(
    private val kafkaTemplate: KafkaTemplate<String, NotificationEvent>,
) : NotificationEventCallbackService<CompletableFuture<*>> {
    override fun process(notificationEvent: NotificationEvent): CompletableFuture<SendResult<String, NotificationEvent>> =
        kafkaTemplate.send(KafkaConfig.NOTIFICATION_TOPIC, notificationEvent)

    override fun onSuccess(param: CompletableFuture<*>) {
        param.join()
    }

    override fun onFailure(exception: Exception) {
        logger.error { "Failed to send notification event to Kafka: ${exception.message}" }
    }
}
