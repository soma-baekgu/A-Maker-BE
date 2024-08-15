package com.backgu.amaker.infra.common.id

import com.backgu.amaker.common.id.IdPublisher
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UUIDIdPublisher : IdPublisher {
    override fun publishId(): String = UUID.randomUUID().toString()
}
