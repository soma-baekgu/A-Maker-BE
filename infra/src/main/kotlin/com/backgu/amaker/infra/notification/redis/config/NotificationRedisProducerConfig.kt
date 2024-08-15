package com.backgu.amaker.infra.notification.redis.config

import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.infra.notification.redis.service.RedisNotificationEventService
import com.backgu.amaker.infra.redis.config.RedisConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@Import(RedisConfig::class)
class NotificationRedisProducerConfig(
    private val redisConfig: RedisConfig,
) {
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory = LettuceConnectionFactory(redisConfig.host, redisConfig.port)

    @Bean
    fun redisTemplate(): RedisTemplate<String, Notification> {
        val template = RedisTemplate<String, Notification>()
        template.connectionFactory = redisConnectionFactory()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = Jackson2JsonRedisSerializer(Notification::class.java)
        return template
    }

    @Bean
    fun redisNotificationEventService(): NotificationEventService = RedisNotificationEventService(redisTemplate())
}
