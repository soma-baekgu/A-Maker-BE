package com.backgu.amaker.api.common.infra

import com.backgu.amaker.api.common.service.LockService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

val logger = KotlinLogging.logger {}

@Service
class RedissonLockService(
    private val redissonClient: RedissonClient,
) : LockService {
    companion object {
        private const val LOCK_PREFIX = "LOCK"

        fun getLockKey(
            domain: String,
            key: String,
        ): String = "$LOCK_PREFIX:$domain:$key"
    }

    override fun tryLock(
        domain: String,
        key: Any,
    ): Boolean {
        val lock = redissonClient.getLock(getLockKey(domain, key.toString()))
        logger.debug { "tryLock domain=$domain key=$key" }
        val res = lock.tryLock(500, 10000, TimeUnit.MILLISECONDS)
        return res
    }

    override fun unlock(
        domain: String,
        key: Any,
    ) {
        val lock = redissonClient.getLock(getLockKey(domain, key.toString()))
        logger.debug { "unlock domain=$domain key=$key" }
        lock.unlock()
    }
}
