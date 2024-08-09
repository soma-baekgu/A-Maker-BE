package com.backgu.amaker.api.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
class RedisConfig {
    lateinit var host: String
    var port: Int = 0

    fun getUrl(): String = "redis://$host:$port"

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val factory = LettuceConnectionFactory(host, port)
        factory.afterPropertiesSet()
        return factory
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Long> {
        val template = RedisTemplate<String, Long>()
        template.connectionFactory = redisConnectionFactory()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        return template
    }
}
