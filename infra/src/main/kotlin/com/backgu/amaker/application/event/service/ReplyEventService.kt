package com.backgu.amaker.application.event.service

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.event.ReplyEvent
import com.backgu.amaker.infra.jpa.event.entity.ReplyEventEntity
import com.backgu.amaker.infra.jpa.event.repository.ReplyEventRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReplyEventService(
    private val replyEventRepository: ReplyEventRepository,
) {
    @Transactional
    fun save(replyEvent: ReplyEvent): ReplyEvent = replyEventRepository.save(ReplyEventEntity.of(replyEvent)).toDomain()

    fun getById(id: Long): ReplyEvent =
        replyEventRepository.findByIdOrNull(id)?.toDomain()
            ?: throw BusinessException(StatusCode.EVENT_NOT_FOUND)
}
