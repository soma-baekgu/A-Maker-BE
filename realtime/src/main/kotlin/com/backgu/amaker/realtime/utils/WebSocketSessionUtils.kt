package com.backgu.amaker.realtime.utils

import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession

class WebSocketSessionUtils {
    companion object {
        inline fun <reified T> extractAttribute(
            session: WebSocketSession,
            key: String,
            throwable: Throwable = IllegalArgumentException("Failed to maintain connection"),
        ): T {
            val attribute: Any? = session.attributes[key]

            if (attribute == null) {
                session.close(CloseStatus.BAD_DATA)
                throw throwable
            }

            return try {
                attribute as T
            } catch (e: ClassCastException) {
                session.close(CloseStatus.BAD_DATA)
                throw throwable
            }
        }
    }
}
