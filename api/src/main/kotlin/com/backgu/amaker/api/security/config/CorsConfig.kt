package com.backgu.amaker.api.security.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "cors")
class CorsConfig(
    var allowedMethods: List<String> = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS"),
    var allowedHeaders: List<String> = listOf("*"),
) {
    lateinit var allowedOrigins: List<String>
}
