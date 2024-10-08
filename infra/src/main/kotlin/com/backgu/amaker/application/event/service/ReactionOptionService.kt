package com.backgu.amaker.application.event.service

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.event.ReactionOption
import com.backgu.amaker.infra.jpa.event.entity.ReactionOptionEntity
import com.backgu.amaker.infra.jpa.event.repository.ReactionOptionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReactionOptionService(
    private val reactionOptionRepository: ReactionOptionRepository,
) {
    @Transactional
    fun saveAll(options: List<ReactionOption>): List<ReactionOption> =
        reactionOptionRepository
            .saveAll(options.map { ReactionOptionEntity.of(it) })
            .map { it.toDomain() }

    fun getAllByEventId(eventId: Long): List<ReactionOption> = reactionOptionRepository.findAllByEventId(eventId).map { it.toDomain() }

    fun validateOptionInEvent(
        optionId: Long,
        eventId: Long,
    ) {
        reactionOptionRepository.findByIdAndEventId(optionId, eventId)
            ?: throw BusinessException(StatusCode.REACTION_OPTION_NOT_FOUND)
    }
}
