package com.backgu.amaker.api.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!prod")
@Configuration
class RedissonConfig(
    private val redisConfig: RedisConfig,
) {
    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.useSingleServer().setAddress(redisConfig.getUrl())
        return Redisson.create(config)
    }
}
