package com.backgu.amaker.infra.jpa.event.repository

import com.backgu.amaker.infra.jpa.event.entity.TaskCommentEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TaskCommentRepository : JpaRepository<TaskCommentEntity, Long> {
    fun findAllByEventId(
        eventId: Long,
        pageable: Pageable,
    ): Page<TaskCommentEntity>
}
