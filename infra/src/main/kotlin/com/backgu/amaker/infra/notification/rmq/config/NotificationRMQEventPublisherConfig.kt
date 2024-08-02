package com.backgu.amaker.infra.notification.rmq.config

import com.backgu.amaker.infra.notification.rmq.serivce.RMQEventPublisher
import com.backgu.amaker.infra.rmq.config.RMQConfig
import org.springframework.amqp.core.AcknowledgeMode
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(RMQConfig::class)
class NotificationRMQEventPublisherConfig(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val jackson2JsonMessageConverter: Jackson2JsonMessageConverter,
) {
    @Bean
    fun rabbitListenerContainerFactory(connectionFactory: ConnectionFactory): RabbitListenerContainerFactory<*> {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(jackson2JsonMessageConverter)
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL)
        return factory
    }

    @Bean
    fun notificationRMQEventPublisher(): RMQEventPublisher = RMQEventPublisher(applicationEventPublisher)
}
