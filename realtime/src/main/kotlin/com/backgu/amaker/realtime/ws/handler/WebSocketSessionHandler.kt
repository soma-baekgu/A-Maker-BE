package com.backgu.amaker.realtime.ws.handler

import com.backgu.amaker.realtime.orchestration.ServerRegister
import com.backgu.amaker.realtime.utils.WebSocketSessionUtils
import com.backgu.amaker.realtime.workspace.service.WorkspaceSessionFacadeService
import com.backgu.amaker.realtime.workspace.session.WorkspaceWebSocketSession
import com.backgu.amaker.realtime.ws.constants.WebSocketConstants.Companion.USER_ID
import com.backgu.amaker.realtime.ws.constants.WebSocketConstants.Companion.WORKSPACE_ID
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketSessionHandler(
    private val workspaceSessionFacadeService: WorkspaceSessionFacadeService,
) : TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId: String = WebSocketSessionUtils.extractAttribute<String>(session, USER_ID)
        val workspaceId = WebSocketSessionUtils.extractAttribute<Long>(session, WORKSPACE_ID)
        workspaceSessionFacadeService.enrollUserToWorkspaceSession(
            userId,
            workspaceId,
            WorkspaceWebSocketSession(session.id, userId, workspaceId, ServerRegister.serverId, session),
        )
    }

    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus,
    ) {
        val userId: String = WebSocketSessionUtils.extractAttribute<String>(session, USER_ID)
        val workspaceId = WebSocketSessionUtils.extractAttribute<Long>(session, WORKSPACE_ID)

        workspaceSessionFacadeService.dropOutWorkspaceSession(
            userId,
            workspaceId,
            WorkspaceWebSocketSession(session.id, userId, workspaceId, ServerRegister.serverId, session),
        )
    }
}
