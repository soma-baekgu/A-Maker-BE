package com.backgu.amaker.realtime.workspace.repository

import com.backgu.amaker.domain.workspace.WorkspaceSession
import com.backgu.amaker.infra.redis.workspace.dto.WorkspaceSessionRedisData
import com.backgu.amaker.infra.redis.workspace.repository.WorkspaceSessionRepository
import com.backgu.amaker.realtime.common.container.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import java.util.UUID

@DisplayName("WorkspaceSessionRepository 테스트")
class WorkspaceSessionRepositoryTest : IntegrationTest() {
    @Autowired
    lateinit var workspaceSessionRepository: WorkspaceSessionRepository

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, WorkspaceSessionRedisData>

    @Test
    @DisplayName("addWorkspaceSession 테스트")
    fun addWorkspaceSessionTest() {
        // given
        val workspaceId = 1L
        val workspaceSession = WorkspaceSession(UUID.randomUUID().toString(), "userId", workspaceId, 1)

        // when
        workspaceSessionRepository.addWorkspaceSession(workspaceId, workspaceSession)

        // then
        val workspaceSessions: List<WorkspaceSessionRedisData> =
            redisTemplate.opsForList().range(WorkspaceSessionRepository.key(workspaceId), 0, -1)!!
        assertThat(workspaceSessions).isNotNull()
        assertThat(workspaceSessions.size).isEqualTo(1)
        assertThat(workspaceSessions[0].workspaceId).isEqualTo(workspaceSession.workspaceId)
        assertThat(workspaceSessions[0].userId).isEqualTo(workspaceSession.userId)
        assertThat(workspaceSessions[0].realtimeId).isEqualTo(workspaceSession.realtimeId)
    }
}
