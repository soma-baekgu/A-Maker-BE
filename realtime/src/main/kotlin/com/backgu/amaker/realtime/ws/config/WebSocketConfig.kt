package com.backgu.amaker.realtime.ws.config

import com.backgu.amaker.realtime.security.AttributeInitializerInterceptor
import com.backgu.amaker.realtime.ws.constants.WebSocketConstants
import com.backgu.amaker.realtime.ws.constants.WebSocketConstants.Companion.WS_WORKSPACE_ENDPOINT
import com.backgu.amaker.realtime.ws.handler.WebSocketSessionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val webSocketSessionHandler: WebSocketSessionHandler,
    private val attributeInitializerInterceptor: AttributeInitializerInterceptor,
    private val webSocketConstants: WebSocketConstants,
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry
            .addHandler(webSocketSessionHandler, WS_WORKSPACE_ENDPOINT)
            .addInterceptors(attributeInitializerInterceptor)
            .setAllowedOrigins(*webSocketConstants.allowedOrigins.toTypedArray())
    }
}
