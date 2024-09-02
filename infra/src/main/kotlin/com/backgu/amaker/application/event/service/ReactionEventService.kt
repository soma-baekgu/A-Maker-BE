package com.backgu.amaker.application.event.service

import com.backgu.amaker.domain.event.ReactionEvent
import com.backgu.amaker.infra.jpa.event.entity.ReactionEventEntity
import com.backgu.amaker.infra.jpa.event.repository.ReactionEventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReactionEventService(
    private val reactionEventRepository: ReactionEventRepository,
) {
    @Transactional
    fun save(reactionEvent: ReactionEvent): ReactionEvent = reactionEventRepository.save(ReactionEventEntity.of(reactionEvent)).toDomain()
}
