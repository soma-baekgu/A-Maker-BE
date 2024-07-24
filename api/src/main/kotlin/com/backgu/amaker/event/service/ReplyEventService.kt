package com.backgu.amaker.event.service

import com.backgu.amaker.event.domain.ReplyEvent
import com.backgu.amaker.event.jpa.ReplyEventEntity
import com.backgu.amaker.event.repository.ReplyEventRepository
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
