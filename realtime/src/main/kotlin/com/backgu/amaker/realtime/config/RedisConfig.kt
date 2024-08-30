package com.backgu.amaker.realtime.config

import com.backgu.amaker.infra.redis.session.SessionRedisData
import com.backgu.amaker.realtime.server.config.ServerConfig
import com.backgu.amaker.realtime.session.service.SessionDeleteSubscriber
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@Profile("!prod")
@ConfigurationProperties(prefix = "spring.data.redis")
class RedisConfig {
    lateinit var host: String
    var port: Int = 0

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val factory = LettuceConnectionFactory(host, port)
        factory.afterPropertiesSet()
        return factory
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, SessionRedisData> {
        val template = RedisTemplate<String, SessionRedisData>()
        template.connectionFactory = redisConnectionFactory()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        return template
    }

    @Bean
    fun messageListenerAdapter(sessionDeleteSubscriber: SessionDeleteSubscriber): MessageListenerAdapter =
        MessageListenerAdapter(sessionDeleteSubscriber, "dropOutSessions")

    @Bean
    fun redisContainer(
        messageListenerAdapter: MessageListenerAdapter,
        topic: ChannelTopic,
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(redisConnectionFactory())
        container.addMessageListener(messageListenerAdapter, topic)
        return container
    }

    @Bean
    fun topic(serverConfig: ServerConfig): ChannelTopic = ChannelTopic(serverConfig.id)
}
