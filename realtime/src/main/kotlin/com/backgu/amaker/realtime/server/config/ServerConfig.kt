package com.backgu.amaker.realtime.server.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.net.InetAddress

@Configuration
@ConfigurationProperties(prefix = "server")
class ServerConfig {
    var address: String = InetAddress.getLocalHost().hostAddress
    lateinit var id: String
    var port: Int = 8080
}
