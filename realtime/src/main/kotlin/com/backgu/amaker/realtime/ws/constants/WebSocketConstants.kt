package com.backgu.amaker.realtime.ws.constants

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "websocket")
class WebSocketConstants {
    lateinit var allowedOrigins: List<String>

    companion object {
        const val WS_WORKSPACE_ENDPOINT = "/ws/v1/workspaces/{workspace-id}"
        const val USER_ID = "user-id"
        const val WORKSPACE_ID = "workspace-id"
    }
}
