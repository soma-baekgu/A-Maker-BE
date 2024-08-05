package com.backgu.amaker.realtime.workspace.service

import com.backgu.amaker.realtime.workspace.session.WorkspaceWebSocketSession
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession

@Service
class WorkspaceSessionFacadeService(
    private val workspaceUserService: WorkspaceUserService,
    private val workspaceSessionService: WorkspaceSessionService,
) {
    fun enrollUserToWorkspaceSession(
        userId: String,
        workspaceId: Long,
        workspaceSession: WorkspaceWebSocketSession<WebSocketSession>,
    ) {
        workspaceUserService.checkUserBelongToWorkspace(userId, workspaceId)
        workspaceSessionService.enrollUserToWorkspaceSession(userId, workspaceId, workspaceSession)
    }

    fun dropOutWorkspaceSession(
        userId: String,
        workspaceId: Long,
        workspaceSession: WorkspaceWebSocketSession<WebSocketSession>,
    ) {
        workspaceSessionService.dropOut(userId, workspaceId, workspaceSession)
    }
}
