package com.backgu.amaker.realtime.ws.handler

import com.backgu.amaker.realtime.utils.WebSocketSessionUtils
import com.backgu.amaker.realtime.workspace.service.WorkspaceUserService
import com.backgu.amaker.realtime.ws.constants.WebSocketConstants.Companion.USER_ID
import com.backgu.amaker.realtime.ws.constants.WebSocketConstants.Companion.WORKSPACE_ID
import com.backgu.amaker.realtime.ws.session.SessionInfo
import com.backgu.amaker.realtime.ws.session.storage.SessionStorage
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketSessionHandler(
    private val storage: SessionStorage<Long, String, SessionInfo>,
    private val workspaceUserService: WorkspaceUserService,
) : TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId: String = WebSocketSessionUtils.extractAttribute<String>(session, USER_ID)
        val workspaceId = WebSocketSessionUtils.extractAttribute<Long>(session, WORKSPACE_ID)

        workspaceUserService.checkUserBelongToWorkspace(userId, workspaceId)

        storage.addSession(workspaceId, userId, SessionInfo(userId, workspaceId, session))
    }

    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus,
    ) {
        val userId: String = WebSocketSessionUtils.extractAttribute<String>(session, USER_ID)
        val workspaceId = WebSocketSessionUtils.extractAttribute<Long>(session, WORKSPACE_ID)

        storage.removeSession(workspaceId, userId)
    }
}
