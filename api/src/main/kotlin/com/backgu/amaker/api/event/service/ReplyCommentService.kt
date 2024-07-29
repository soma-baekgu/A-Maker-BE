package com.backgu.amaker.api.event.service

import com.backgu.amaker.domain.event.ReplyComment
import com.backgu.amaker.infra.jpa.event.entity.ReplyCommentEntity
import com.backgu.amaker.infra.jpa.event.repository.ReplyCommentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReplyCommentService(
    private val replyCommentRepository: ReplyCommentRepository,
) {
    @Transactional
    fun save(replyComment: ReplyComment): ReplyComment = replyCommentRepository.save(ReplyCommentEntity.of(replyComment)).toDomain()

    fun findAllByEventId(
        eventId: Long,
        pageable: Pageable,
    ): Page<ReplyComment> = replyCommentRepository.findAllByEventId(eventId, pageable).map { it.toDomain() }
}
