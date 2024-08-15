package com.backgu.amaker.realtime.session.service

import com.backgu.amaker.application.workspace.WorkspaceUserService
import com.backgu.amaker.realtime.session.session.RealTimeSession
import com.backgu.amaker.realtime.user.service.UserSessionService
import com.backgu.amaker.realtime.workspace.service.WorkspaceSessionService
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession

@Service
class SessionFacadeService(
    private val workspaceUserService: WorkspaceUserService,
    private val workspaceSessionService: WorkspaceSessionService,
    private val userSessionService: UserSessionService,
) {
    fun enrollUserToWorkspaceSession(
        userId: String,
        workspaceId: Long,
        workspaceRealTimeSession: RealTimeSession<WebSocketSession>,
    ) {
        workspaceUserService.validUserInWorkspace(userId, workspaceId)
        workspaceSessionService.enrollUserToWorkspaceSession(workspaceId, workspaceRealTimeSession)
        userSessionService.enrollUserToUserSession(userId, workspaceRealTimeSession)
    }

    fun dropOutWorkspaceSession(
        userId: String,
        workspaceId: Long,
        workspaceRealTimeSession: RealTimeSession<WebSocketSession>,
    ) {
        userSessionService.dropOut(userId, workspaceRealTimeSession)
        workspaceSessionService.dropOut(workspaceId, workspaceRealTimeSession)
    }
}
