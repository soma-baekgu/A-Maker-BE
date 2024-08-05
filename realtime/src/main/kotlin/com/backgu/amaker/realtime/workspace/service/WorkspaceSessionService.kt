package com.backgu.amaker.realtime.workspace.service

import com.backgu.amaker.infra.redis.workspace.repository.WorkspaceSessionRepository
import com.backgu.amaker.realtime.workspace.session.WorkspaceWebSocketSession
import com.backgu.amaker.realtime.workspace.storage.SessionStorage
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession

@Service
class WorkspaceSessionService(
    private val sessionStorage: SessionStorage,
    private val workspaceSessionRepository: WorkspaceSessionRepository,
) {
    fun enrollUserToWorkspaceSession(
        userId: String,
        workspaceId: Long,
        session: WorkspaceWebSocketSession<WebSocketSession>,
    ) {
        sessionStorage.addSession(session)
        workspaceSessionRepository.addWorkspaceSession(
            workspaceId,
            session.toDomain(),
        )
    }

    fun dropOut(
        userId: String,
        workspaceId: Long,
        session: WorkspaceWebSocketSession<WebSocketSession>,
    ) {
        sessionStorage.removeSession(session.id)
    }
}
