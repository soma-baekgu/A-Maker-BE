package com.backgu.amaker.infra.notification.redis.config

import com.backgu.amaker.infra.notification.redis.service.RedisEventPublisher
import com.backgu.amaker.infra.redis.config.RedisConfig
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter

@Configuration
@Import(RedisConfig::class)
class NotificationRedisEventPublisherConfig(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val redisConfig: RedisConfig,
) {
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory = LettuceConnectionFactory(redisConfig.host, redisConfig.port)

    @Bean
    fun messageListener(): MessageListenerAdapter = MessageListenerAdapter(RedisEventPublisher(applicationEventPublisher))

    @Bean
    fun redisContainer(): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(redisConnectionFactory())
        container.addMessageListener(messageListener(), ChannelTopic(RedisConfig.NOTIFICATION_CHANNEL))
        return container
    }
}
