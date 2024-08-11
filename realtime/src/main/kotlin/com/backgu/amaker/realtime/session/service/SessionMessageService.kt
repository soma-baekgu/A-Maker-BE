package com.backgu.amaker.realtime.session.service

import com.backgu.amaker.realtime.session.session.RealTimeSession
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

@Service
class SessionMessageService(
    private val objectMapper: ObjectMapper,
) {
    fun sendMessageToSession(
        session: RealTimeSession<WebSocketSession>,
        message: Any,
    ): Boolean =
        try {
            session.session.sendMessage(TextMessage(objectMapper.writeValueAsBytes(message)))
            true
        } catch (e: Exception) {
            false
        }
}
