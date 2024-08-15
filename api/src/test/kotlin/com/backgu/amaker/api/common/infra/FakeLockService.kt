package com.backgu.amaker.api.common.infra

import com.backgu.amaker.api.common.annotation.IntegrationTestComponent
import com.backgu.amaker.api.common.service.LockService

@IntegrationTestComponent
class FakeLockService : LockService {
    override fun tryLock(
        domain: String,
        key: Any,
    ): Boolean {
        // always return true
        return true
    }

    override fun unlock(
        domain: String,
        key: Any,
    ) {
        // do nothing
    }
}
