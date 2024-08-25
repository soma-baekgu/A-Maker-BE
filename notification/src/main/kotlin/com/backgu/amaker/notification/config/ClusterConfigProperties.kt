package com.backgu.amaker.notification.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis.cluster")
class ClusterConfigProperties {
    lateinit var nodes: List<String>
    var maxRedirects: Int = 3
}
