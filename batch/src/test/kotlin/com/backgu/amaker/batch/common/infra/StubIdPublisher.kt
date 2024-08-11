package com.backgu.amaker.batch.common.infra

import org.springframework.stereotype.Component

@Component
class StubIdPublisher {
    companion object {
        var counter = 1
    }

    fun reset() {
        counter = 1
    }

    fun publishId(): String = (counter++).toString()
}
