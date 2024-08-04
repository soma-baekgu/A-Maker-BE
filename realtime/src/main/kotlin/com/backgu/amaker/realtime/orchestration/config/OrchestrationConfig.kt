package com.backgu.amaker.realtime.orchestration.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "orchestration")
class OrchestrationConfig {
    companion object {
        const val END_POINT = "/system/v1/realtime"
    }

    lateinit var baseUrl: String
    var openPort: Int = 0

    @Value("\${orchestration.user.id}")
    lateinit var systemUserId: String
}
