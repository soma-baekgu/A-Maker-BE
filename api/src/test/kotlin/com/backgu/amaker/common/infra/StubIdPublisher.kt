package com.backgu.amaker.common.infra

import com.backgu.amaker.common.annotation.IntegrationTestComponent
import com.backgu.amaker.common.service.IdPublisher

@IntegrationTestComponent
class StubIdPublisher : IdPublisher {
    companion object {
        var counter = 1
    }

    fun reset() {
        counter = 1
    }

    override fun publishId(): String = (counter++).toString()
}
