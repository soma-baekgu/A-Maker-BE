package com.backgu.amaker.infra.jpa.event.repository

import com.backgu.amaker.infra.jpa.event.entity.ReactionCommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReactionCommentRepository : JpaRepository<ReactionCommentEntity, Long> {
    fun findByEventIdAndUserId(
        eventId: Long,
        userId: String,
    ): ReactionCommentEntity?

    fun findByEventId(eventId: Long): List<ReactionCommentEntity>
}
