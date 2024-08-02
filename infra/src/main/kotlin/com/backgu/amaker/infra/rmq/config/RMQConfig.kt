package com.backgu.amaker.infra.rmq.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
class RMQConfig {
    companion object {
        const val NOTIFICATION_QUEUE = "notification"
        const val NOTIFICATION_EXCHANGE = "notification"
        const val NOTIFICATION_ROUTING_KEY = "notification"
    }

    lateinit var host: String
    var port: Int = 0
    lateinit var username: String
    lateinit var password: String

    @Bean
    fun jackson2JsonMessageConverter(): Jackson2JsonMessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun queue(): Queue = Queue(RMQConfig.NOTIFICATION_QUEUE, false)

    @Bean
    fun topicExchange(): TopicExchange = TopicExchange(RMQConfig.NOTIFICATION_EXCHANGE)

    @Bean
    fun binding(): Binding = BindingBuilder.bind(queue()).to(topicExchange()).with(RMQConfig.NOTIFICATION_ROUTING_KEY)

    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory(host, port)
        connectionFactory.username = username
        connectionFactory.setPassword(password)
        return connectionFactory
    }
}
