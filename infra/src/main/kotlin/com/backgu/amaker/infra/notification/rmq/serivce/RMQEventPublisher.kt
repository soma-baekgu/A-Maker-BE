package com.backgu.amaker.infra.notification.rmq.serivce

import com.backgu.amaker.application.notification.event.NotificationEventWithCallback
import com.backgu.amaker.application.notification.mail.event.EmailEvent
import com.backgu.amaker.infra.rmq.config.RMQConfig
import com.rabbitmq.client.Channel
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.context.ApplicationEventPublisher
import org.springframework.messaging.handler.annotation.Headers

class RMQEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @RabbitListener(queues = [RMQConfig.NOTIFICATION_QUEUE])
    fun publish(
        notificationEvent: EmailEvent,
        channel: Channel,
        @Headers headers: Map<String, Any>,
    ) {
        val deliveryTag = headers[AmqpHeaders.DELIVERY_TAG] as Long

        applicationEventPublisher.publishEvent(
            object : NotificationEventWithCallback {
                override val notificationEvent = notificationEvent

                override fun postHandle() {
                    channel.basicAck(deliveryTag, false)
                }

                override fun onFail(exception: Exception) {
                    channel.basicNack(deliveryTag, false, true)
                }
            },
        )
    }
}
