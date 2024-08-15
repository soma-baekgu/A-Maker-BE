package com.backgu.amaker.infra.redis.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
class RedisConfig {
    lateinit var host: String
    var port: Int = 0

    companion object {
        const val NOTIFICATION_CHANNEL = "notification"
    }
}
