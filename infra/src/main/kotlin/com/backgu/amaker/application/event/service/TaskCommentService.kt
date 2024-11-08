package com.backgu.amaker.application.event.service

import com.backgu.amaker.domain.event.TaskComment
import com.backgu.amaker.infra.jpa.event.entity.TaskCommentEntity
import com.backgu.amaker.infra.jpa.event.repository.TaskCommentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TaskCommentService(
    private val taskCommentRepository: TaskCommentRepository,
) {
    @Transactional
    fun save(taskComment: TaskComment): TaskComment = taskCommentRepository.save(TaskCommentEntity.of(taskComment)).toDomain()

    fun findAllByEventId(
        eventId: Long,
        pageable: Pageable,
    ): Page<TaskComment> = taskCommentRepository.findAllByEventId(eventId, pageable).map { it.toDomain() }
}
