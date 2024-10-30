package com.backgu.amaker.application.event.service

import com.backgu.amaker.domain.event.ReactionComment
import com.backgu.amaker.domain.event.ReactionEvent
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.infra.jpa.event.entity.ReactionCommentEntity
import com.backgu.amaker.infra.jpa.event.repository.ReactionCommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReactionCommentService(
    private val reactionCommentRepository: ReactionCommentRepository,
) {
    @Transactional
    fun save(reactionComment: ReactionComment): ReactionComment =
        reactionCommentRepository.save(ReactionCommentEntity.of(reactionComment)).toDomain()

    fun findByEventIdAndUserId(
        event: ReactionEvent,
        user: User,
    ): ReactionComment? = reactionCommentRepository.findByEventIdAndUserId(event.id, user.id)?.toDomain()

    fun findAllByEventIdGroupByReactionOptions(eventId: Long): Map<Long, List<ReactionComment>> {
        return reactionCommentRepository.findByEventId(eventId).map { it.toDomain() }.groupBy { it.optionId }
    }
}
