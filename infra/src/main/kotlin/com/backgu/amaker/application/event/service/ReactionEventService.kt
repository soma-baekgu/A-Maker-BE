package com.backgu.amaker.application.event.service

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.event.ReactionEvent
import com.backgu.amaker.infra.jpa.event.entity.ReactionEventEntity
import com.backgu.amaker.infra.jpa.event.repository.ReactionEventRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReactionEventService(
    private val reactionEventRepository: ReactionEventRepository,
) {
    @Transactional
    fun save(reactionEvent: ReactionEvent): ReactionEvent = reactionEventRepository.save(ReactionEventEntity.of(reactionEvent)).toDomain()

    fun getById(id: Long): ReactionEvent =
        reactionEventRepository.findByIdOrNull(id)?.toDomain()
            ?: throw BusinessException(StatusCode.EVENT_NOT_FOUND)
}
