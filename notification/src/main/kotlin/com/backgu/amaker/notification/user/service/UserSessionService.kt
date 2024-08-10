package com.backgu.amaker.notification.user.service

import com.backgu.amaker.domain.session.Session
import com.backgu.amaker.infra.redis.session.user.repository.UserSessionRepository
import org.springframework.stereotype.Service

@Service
class UserSessionService(
    private val userSessionRepository: UserSessionRepository,
) {
    fun findByUserId(userId: String): List<Session> =
        userSessionRepository
            .findUserSessionByUserId(userId)
            .map { it.toDomain() }
}
