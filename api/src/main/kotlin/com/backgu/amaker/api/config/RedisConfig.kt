package com.backgu.amaker.api.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
class RedisConfig {
    lateinit var host: String
    var port: Int = 0

    fun getUrl(): String = "redis://$host:$port"
}
