package com.backgu.amaker.infra.redis.session.user.repository

import com.backgu.amaker.domain.session.Session
import com.backgu.amaker.infra.redis.session.SessionRedisData
import org.springframework.data.redis.core.ListOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class UserSessionRepository(
    private val redisTemplate: RedisTemplate<String, SessionRedisData>,
    private val listOps: ListOperations<String, SessionRedisData> = redisTemplate.opsForList(),
) {
    companion object {
        const val PREFIX = "ws:user:"

        fun key(userId: String) = "$PREFIX$userId"
    }

    fun getUserSession(userId: String): List<SessionRedisData> = listOps.range(key(userId), 0, -1) ?: emptyList()

    fun addUserSession(
        userId: String,
        session: Session,
    ) {
        listOps.leftPush(key(userId), SessionRedisData.of(session))
    }

    fun findUserSessionByUserId(userId: String): List<SessionRedisData> = listOps.range(key(userId), 0, -1) ?: emptyList()

    fun removeUserSession(
        userId: String,
        session: Session,
    ) {
        listOps.remove(key(userId), 1, SessionRedisData.of(session))
    }
}
