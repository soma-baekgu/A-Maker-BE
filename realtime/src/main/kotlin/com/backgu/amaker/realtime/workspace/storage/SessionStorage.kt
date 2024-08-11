package com.backgu.amaker.realtime.workspace.storage

import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.realtime.common.excpetion.RealTimeException
import com.backgu.amaker.realtime.session.session.RealTimeSession
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class SessionStorage {
    private val sessionsMap = ConcurrentHashMap<String, RealTimeSession<WebSocketSession>>()

    fun addSession(session: RealTimeSession<WebSocketSession>) {
        sessionsMap[session.id] = session
    }

    fun removeSession(id: String) {
        sessionsMap.remove(id)
    }

    fun getSessions(ids: Collection<String>): List<RealTimeSession<WebSocketSession>> = ids.mapNotNull { sessionsMap[it] }

    fun getSession(id: String): RealTimeSession<WebSocketSession> =
        sessionsMap[id] ?: throw RealTimeException(StatusCode.INTERNAL_SERVER_ERROR)
}
