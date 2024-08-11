package com.backgu.amaker.infra.redis.session.workspace.repository

import com.backgu.amaker.domain.session.Session
import com.backgu.amaker.infra.redis.session.SessionRedisData
import org.springframework.data.redis.core.ListOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class WorkspaceSessionRepository(
    private val redisTemplate: RedisTemplate<String, SessionRedisData>,
    private val listOps: ListOperations<String, SessionRedisData> = redisTemplate.opsForList(),
) {
    companion object {
        const val PREFIX = "ws:workspace:"

        fun key(workspaceId: Long) = "$PREFIX$workspaceId"
    }

    fun addWorkspaceSession(
        workspaceId: Long,
        session: Session,
    ) {
        listOps.leftPush(key(workspaceId), SessionRedisData.of(session))
    }

    fun findWorkspaceSessionByWorkspaceId(workspaceId: Long): List<SessionRedisData> = listOps.range(key(workspaceId), 0, -1) ?: emptyList()

    fun getWorkspaceSession(workspaceId: Long): List<SessionRedisData> =
        listOps.range(
            key(
                workspaceId,
            ),
            0,
            -1,
        ) ?: emptyList()

    fun removeWorkspaceSession(
        workspaceId: Long,
        session: Session,
    ) {
        listOps.remove(key(workspaceId), 1, SessionRedisData.of(session))
    }
}
