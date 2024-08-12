package com.backgu.amaker.infra.notification.kafka.service

import com.backgu.amaker.application.notification.event.NotificationEventWithCallback
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.infra.kafka.config.KafkaConfig
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.ApplicationEventPublisher
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment

class KafkaEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @KafkaListener(
        topics = [KafkaConfig.NOTIFICATION_TOPIC],
        groupId = KafkaConfig.NOTIFICATION_GROUP_ID,
        containerFactory = "notificationContainerFactory",
    )
    fun onNotificationEvent(
        record: ConsumerRecord<String, Notification>,
        ack: Acknowledgment,
        consumer: Consumer<String, Notification>,
    ) {
        applicationEventPublisher.publishEvent(
            object : NotificationEventWithCallback {
                override val notification = record.value()

                override fun postHandle() {
                    ack.acknowledge()
                }

                override fun onFail(exception: Exception) {
                    consumer.commitSync()
                }
            },
        )
    }
}
