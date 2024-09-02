package com.backgu.amaker.realtime.session.service

import com.backgu.amaker.application.workspace.WorkspaceUserService
import com.backgu.amaker.infra.redis.session.SessionRedisData
import com.backgu.amaker.realtime.server.config.ServerConfig
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
    private val sessionDeletePublisher: SessionDeletePublisher,
    private val serverConfig: ServerConfig,
) {
    fun enrollUserToWorkspaceSession(
        userId: String,
        workspaceId: Long,
        workspaceRealTimeSession: RealTimeSession<WebSocketSession>,
    ) {
        workspaceUserService.validateUserWorkspaceInActive(userId, workspaceId)

        workspaceSessionService.findDropOutSessionIfLimit(workspaceId, userId).forEach {
            if (it.realtimeId != serverConfig.id) {
                sessionDeletePublisher.publish(it.realtimeId, SessionRedisData.of(it))
            } else {
                workspaceSessionService.dropOut(workspaceId, it)
                userSessionService.dropOut(userId, it)
            }
        }

        workspaceSessionService.enrollUserToWorkspaceSession(workspaceId, workspaceRealTimeSession)
        userSessionService.enrollUserToUserSession(userId, workspaceRealTimeSession)
    }

    fun dropOutWorkspaceSession(
        userId: String,
        workspaceId: Long,
        workspaceRealTimeSession: RealTimeSession<WebSocketSession>,
    ) {
        workspaceSessionService.dropOut(workspaceId, workspaceRealTimeSession)
        userSessionService.dropOut(userId, workspaceRealTimeSession)
    }
}
