package com.backgu.amaker.realtime.ws.session

import org.springframework.web.socket.WebSocketSession

class SessionInfo(
    val userId: String,
    val workspaceId: Long,
    val webSocketSession: WebSocketSession,
) {
    private val attributes = mutableMapOf<String, Any>()

    fun setAttribute(
        key: String,
        value: Any,
    ) {
        attributes[key] = value
    }

    fun getAttribute(key: String): Any? = attributes[key]
}
