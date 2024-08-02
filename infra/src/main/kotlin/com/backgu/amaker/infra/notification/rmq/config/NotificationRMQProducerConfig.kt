package com.backgu.amaker.infra.notification.rmq.config

import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.infra.notification.rmq.serivce.NotificationRMQEventService
import com.backgu.amaker.infra.rmq.config.RMQConfig
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(RMQConfig::class)
class NotificationRMQProducerConfig(
    private val jackson2JsonMessageConverter: Jackson2JsonMessageConverter,
) {
    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jackson2JsonMessageConverter
        return rabbitTemplate
    }

    @Bean
    fun notificationRMQEventService(rabbitTemplate: RabbitTemplate): NotificationEventService = NotificationRMQEventService(rabbitTemplate)
}
