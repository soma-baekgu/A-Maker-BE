package com.backgu.amaker.realtime.workspace.service

import com.backgu.amaker.infra.redis.session.workspace.repository.WorkspaceSessionRepository
import com.backgu.amaker.realtime.server.config.ServerConfig
import com.backgu.amaker.realtime.session.session.RealTimeSession
import com.backgu.amaker.realtime.workspace.storage.SessionStorage
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession

@Service
class WorkspaceSessionService(
    private val sessionStorage: SessionStorage,
    private val workspaceSessionRepository: WorkspaceSessionRepository,
    private val serverConfig: ServerConfig,
) {
    fun enrollUserToWorkspaceSession(
        workspaceId: Long,
        session: RealTimeSession<WebSocketSession>,
    ) {
        sessionStorage.addSession(session)
        workspaceSessionRepository.addWorkspaceSession(
            workspaceId,
            session.toDomain(),
        )
    }

    fun dropOut(
        workspaceId: Long,
        session: RealTimeSession<WebSocketSession>,
    ) {
        workspaceSessionRepository.removeWorkspaceSession(workspaceId, session.toDomain())
        sessionStorage.removeSession(session.id)
    }

    fun getWorkspaceSession(workspaceId: Long): List<RealTimeSession<WebSocketSession>> {
        val workspaceSessions =
            workspaceSessionRepository
                .findWorkspaceSessionByWorkspaceId(workspaceId)
                .map { it.toDomain() }
                .filter { it.isBelongToServer(serverConfig.id) }

        return sessionStorage.getSessions(workspaceSessions.map { it.id })
    }
}
