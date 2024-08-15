package com.backgu.amaker.api.common.service

interface LockService {
    fun tryLock(
        domain: String,
        key: Any,
    ): Boolean

    fun unlock(
        domain: String,
        key: Any,
    )
}
