package com.backgu.amaker.api.common.infra

import com.backgu.amaker.api.common.service.IdPublisher
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UUIDIdPublisher : IdPublisher {
    override fun publishId(): String = UUID.randomUUID().toString()
}
