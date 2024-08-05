package com.backgu.amaker.realtime.workspace.storage

import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.realtime.common.excpetion.RealTimeException
import com.backgu.amaker.realtime.workspace.session.WorkspaceWebSocketSession
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class SessionStorage {
    private val sessionsMap = ConcurrentHashMap<String, WorkspaceWebSocketSession<WebSocketSession>>()

    fun addSession(session: WorkspaceWebSocketSession<WebSocketSession>) {
        sessionsMap[session.id] = session
    }

    fun removeSession(id: String) {
        sessionsMap.remove(id)
    }

    fun getSession(id: String): WorkspaceWebSocketSession<WebSocketSession> =
        sessionsMap[id] ?: throw RealTimeException(StatusCode.INTERNAL_SERVER_ERROR)
}
