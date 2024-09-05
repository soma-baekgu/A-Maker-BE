package com.backgu.amaker.realtime.session.service

import com.backgu.amaker.infra.redis.session.SessionRedisData
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class SessionDeletePublisher(
    private val redisTemplate: RedisTemplate<String, SessionRedisData>,
) {
    fun publish(
        serverId: String,
        sessionRedisData: SessionRedisData,
    ) {
        redisTemplate.convertAndSend(serverId, sessionRedisData)
    }
}
