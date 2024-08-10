package com.backgu.amaker.realtime.workspace.repository

import com.backgu.amaker.domain.session.Session
import com.backgu.amaker.infra.redis.session.SessionRedisData
import com.backgu.amaker.infra.redis.session.workspace.repository.WorkspaceSessionRepository
import com.backgu.amaker.realtime.common.container.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import java.util.UUID

@DisplayName("WorkspaceSessionRepository 테스트")
class SessionRepositoryTest : IntegrationTest() {
    @Autowired
    lateinit var workspaceSessionRepository: WorkspaceSessionRepository

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, SessionRedisData>

    @Test
    @DisplayName("addWorkspaceSession 테스트")
    fun addWorkspaceSessionTest() {
        // given
        val workspaceId = 1L
        val session = Session(UUID.randomUUID().toString(), "userId", workspaceId, "1")

        // when
        workspaceSessionRepository.addWorkspaceSession(workspaceId, session)

        // then
        val workspaceSessions: List<SessionRedisData> =
            redisTemplate.opsForList().range(WorkspaceSessionRepository.key(workspaceId), 0, -1)!!
        assertThat(workspaceSessions).isNotNull()
        assertThat(workspaceSessions.size).isEqualTo(1)
        assertThat(workspaceSessions[0].workspaceId).isEqualTo(session.workspaceId)
        assertThat(workspaceSessions[0].userId).isEqualTo(session.userId)
        assertThat(workspaceSessions[0].realtimeId).isEqualTo(session.realtimeId)
    }
}
