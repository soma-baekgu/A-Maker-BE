package com.backgu.amaker.infra.kafka.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("spring.kafka")
class KafkaConfig {
    lateinit var bootstrapServers: List<String>

    companion object {
        const val NOTIFICATION_TOPIC = "notification"
        const val NOTIFICATION_GROUP_ID = "notification-group"
        const val NOTIFICATION_CONCURRENCY = 1
    }
}
