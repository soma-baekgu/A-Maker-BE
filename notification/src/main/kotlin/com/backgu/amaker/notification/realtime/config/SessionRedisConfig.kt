package com.backgu.amaker.notification.realtime.config

import com.backgu.amaker.infra.redis.session.SessionRedisData
import com.backgu.amaker.infra.redis.session.user.repository.UserSessionRepository
import com.backgu.amaker.infra.redis.session.workspace.repository.WorkspaceSessionRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class SessionRedisConfig {
    @Bean
    fun workspaceSessionRepository(redisTemplate: RedisTemplate<String, SessionRedisData>): WorkspaceSessionRepository =
        WorkspaceSessionRepository(redisTemplate)

    @Bean
    fun userSessionRepository(redisTemplate: RedisTemplate<String, SessionRedisData>): UserSessionRepository =
        UserSessionRepository(redisTemplate)
}
