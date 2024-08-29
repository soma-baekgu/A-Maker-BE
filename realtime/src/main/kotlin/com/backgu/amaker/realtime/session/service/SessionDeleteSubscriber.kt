package com.backgu.amaker.realtime.session.service

import com.backgu.amaker.infra.redis.session.SessionRedisData
import com.backgu.amaker.realtime.user.service.UserSessionService
import com.backgu.amaker.realtime.workspace.service.WorkspaceSessionService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class SessionDeleteSubscriber(
    private val workspaceSessionService: WorkspaceSessionService,
    private val userSessionService: UserSessionService,
    private val objectMapper: ObjectMapper,
) {
    fun dropOutSessions(message: String) {
        val sessionRedisData = objectMapper.readValue(message, SessionRedisData::class.java)
        workspaceSessionService.dropOut(sessionRedisData.workspaceId, sessionRedisData.toDomain())
        userSessionService.dropOut(sessionRedisData.userId, sessionRedisData.toDomain())
    }
}
