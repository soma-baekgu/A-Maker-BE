package com.backgu.amaker.api.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("prod")
class ProdRedissonConfig(
    private val clusterConfigProperties: ClusterConfigProperties,
) {
    @Value("\${spring.data.redis.password}")
    lateinit var password: String

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        val clusterServersConfig =
            config
                .useClusterServers()
                .setScanInterval(2000)
                .setConnectTimeout(100)
                .setTimeout(3000)
                .setRetryAttempts(3)
                .setRetryInterval(1500)

        clusterConfigProperties.nodes.forEach { node ->
            clusterServersConfig.addNodeAddress("redis://$node")
        }

        if (password.isNotEmpty()) {
            clusterServersConfig.setPassword(password)
        }

        return Redisson.create(config)
    }
}
