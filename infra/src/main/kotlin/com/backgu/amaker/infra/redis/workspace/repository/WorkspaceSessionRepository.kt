package com.backgu.amaker.infra.redis.workspace.repository

import com.backgu.amaker.domain.workspace.WorkspaceSession
import com.backgu.amaker.infra.redis.workspace.dto.WorkspaceSessionRedisData
import org.springframework.data.redis.core.ListOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class WorkspaceSessionRepository(
    private val redisTemplate: RedisTemplate<String, WorkspaceSessionRedisData>,
    private val listOps: ListOperations<String, WorkspaceSessionRedisData> = redisTemplate.opsForList(),
) {
    companion object {
        const val PREFIX = "workspace:"

        fun key(workspaceId: Long) = "$PREFIX$workspaceId"
    }

    fun addWorkspaceSession(
        workspaceId: Long,
        workspaceSession: WorkspaceSession,
    ) {
        listOps.leftPush(key(workspaceId), WorkspaceSessionRedisData.of(workspaceSession))
    }
}
