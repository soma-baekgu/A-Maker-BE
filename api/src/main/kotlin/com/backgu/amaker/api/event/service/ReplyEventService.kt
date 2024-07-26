package com.backgu.amaker.api.event.service

import com.backgu.amaker.domain.event.ReplyEvent
import com.backgu.amaker.infra.jpa.event.entity.ReplyEventEntity
import com.backgu.amaker.infra.jpa.event.repository.ReplyEventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReplyEventService(
    private val replyEventRepository: ReplyEventRepository,
) {
    @Transactional
    fun save(replyEvent: ReplyEvent): ReplyEvent = replyEventRepository.save(ReplyEventEntity.of(replyEvent)).toDomain()
}
