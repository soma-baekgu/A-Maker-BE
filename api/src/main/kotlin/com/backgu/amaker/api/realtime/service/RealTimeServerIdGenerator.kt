package com.backgu.amaker.api.realtime.service

import jakarta.annotation.PostConstruct
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RealTimeServerIdGenerator(
    private val redisTemplate: RedisTemplate<String, Long>,
) {
    companion object {
        private const val REALTIME_NEXT_ID_KEY = "realtime:next-id"
        private const val REALTIME_SERVER_INITIAL_VALUE = 1L
    }

    @PostConstruct
    fun init(): Long {
        if (!redisTemplate.hasKey(REALTIME_NEXT_ID_KEY)) {
            redisTemplate
                .opsForValue()
                .set(REALTIME_NEXT_ID_KEY, REALTIME_SERVER_INITIAL_VALUE)
        }
        return REALTIME_SERVER_INITIAL_VALUE
    }

    fun generateId(): Long = redisTemplate.opsForValue().increment(REALTIME_NEXT_ID_KEY) ?: init()
}
