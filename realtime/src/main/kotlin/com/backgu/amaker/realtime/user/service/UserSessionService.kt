package com.backgu.amaker.realtime.user.service

import com.backgu.amaker.infra.redis.session.user.repository.UserSessionRepository
import com.backgu.amaker.realtime.server.config.ServerConfig
import com.backgu.amaker.realtime.session.session.RealTimeSession
import com.backgu.amaker.realtime.workspace.storage.SessionStorage
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession

@Service
class UserSessionService(
    private val sessionStorage: SessionStorage,
    private val userSessionRepository: UserSessionRepository,
    private val serverConfig: ServerConfig,
) {
    fun getUserSession(userId: String): List<RealTimeSession<WebSocketSession>> {
        val userSessions =
            userSessionRepository
                .getUserSession(userId)
                .map { it.toDomain() }
                .filter { it.isBelongToServer(serverConfig.id) }

        return sessionStorage.getSessions(userSessions.map { it.id })
    }

    fun getUserSessionToMap(userIds: List<String>): Map<String, List<RealTimeSession<WebSocketSession>>> =
        userIds.associateWith {
            getUserSession(it)
        }

    fun enrollUserToUserSession(
        userId: String,
        realTimeSession: RealTimeSession<WebSocketSession>,
    ) {
        sessionStorage.addSession(realTimeSession)
        userSessionRepository.addUserSession(
            userId,
            realTimeSession.toDomain(),
        )
    }

    fun dropOut(
        userId: String,
        realTimeSession: RealTimeSession<WebSocketSession>,
    ) {
        userSessionRepository.removeUserSession(
            userId,
            realTimeSession.toDomain(),
        )
        sessionStorage.removeSession(realTimeSession.id)
    }
}
