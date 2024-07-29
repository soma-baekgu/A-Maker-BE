package com.backgu.amaker.infra.jpa.event.repository

import com.backgu.amaker.infra.jpa.event.entity.ReplyCommentEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyCommentRepository : JpaRepository<ReplyCommentEntity, Long> {
    fun findAllByEventId(
        eventId: Long,
        pageable: Pageable,
    ): Page<ReplyCommentEntity>
}
