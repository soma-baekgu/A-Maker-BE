package com.backgu.amaker.realtime.ws.handler

import com.backgu.amaker.realtime.server.config.ServerConfig
import com.backgu.amaker.realtime.session.service.SessionFacadeService
import com.backgu.amaker.realtime.session.session.RealTimeSession
import com.backgu.amaker.realtime.utils.WebSocketSessionUtils
import com.backgu.amaker.realtime.ws.constants.WebSocketConstants.Companion.USER_ID
import com.backgu.amaker.realtime.ws.constants.WebSocketConstants.Companion.WORKSPACE_ID
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketSessionHandler(
    private val sessionFacadeService: SessionFacadeService,
    private val serverConfig: ServerConfig,
) : TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId: String = WebSocketSessionUtils.extractAttribute<String>(session, USER_ID)
        val workspaceId = WebSocketSessionUtils.extractAttribute<Long>(session, WORKSPACE_ID)
        sessionFacadeService.enrollUserToWorkspaceSession(
            userId,
            workspaceId,
            RealTimeSession(session.id, userId, workspaceId, serverConfig.id, session),
        )
    }

    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus,
    ) {
        val userId: String = WebSocketSessionUtils.extractAttribute<String>(session, USER_ID)
        val workspaceId = WebSocketSessionUtils.extractAttribute<Long>(session, WORKSPACE_ID)

        sessionFacadeService.dropOutWorkspaceSession(
            userId,
            workspaceId,
            RealTimeSession(session.id, userId, workspaceId, serverConfig.id, session),
        )
    }
}
