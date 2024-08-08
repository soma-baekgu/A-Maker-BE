package com.backgu.amaker.realtime.ws.session.storage

import com.backgu.amaker.realtime.ws.session.SessionInfo
import org.springframework.stereotype.Component

@Component
class InMemorySessionStorage : SessionStorage<Long, String, SessionInfo> {
    private val sessions = mutableMapOf<Long, List<SessionInfo>>()

    override fun addSession(
        workspaceId: Long,
        userId: String,
        session: SessionInfo,
    ) {
        val workspaceSessions = getWorkspaceSessions(workspaceId).toMutableList()
        workspaceSessions.add(session)
    }

    override fun removeSession(
        workspaceId: Long,
        userId: String,
    ) {
        if (!sessions.containsKey(workspaceId)) return
        val workspaceSessions = getWorkspaceSessions(workspaceId).toMutableList()
        workspaceSessions.removeIf { it.userId == userId }
    }

    override fun getSession(
        workspaceId: Long,
        userId: String,
    ): SessionInfo? {
        if (!sessions.containsKey(workspaceId)) return null
        return getWorkspaceSessions(workspaceId).find { it.userId == userId }
    }

    override fun getWorkspaceSessions(workspaceId: Long): List<SessionInfo> = sessions.getOrDefault(workspaceId, emptyList())
}
