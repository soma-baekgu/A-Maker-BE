package com.backgu.amaker.realtime.server.config

import io.lettuce.core.dynamic.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.net.InetAddress

@Configuration
@ConfigurationProperties(prefix = "server")
class ServerConfig {
    var address: String = InetAddress.getLocalHost().hostAddress
    @Value("\${spring.application.name}")
    lateinit var id: String
    var port: Int = 8080
}
