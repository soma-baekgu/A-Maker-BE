package com.backgu.amaker.api.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "system.user")
class SystemConfig {
    companion object {
        const val REALTIME_SERVER_STATUS_CHECK_INTERVAL = 60 * 1000L
    }

    lateinit var id: String
}
