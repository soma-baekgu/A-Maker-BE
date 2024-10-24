package com.backgu.amaker.application.event.service

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.event.TaskEvent
import com.backgu.amaker.infra.jpa.event.entity.TaskEventEntity
import com.backgu.amaker.infra.jpa.event.repository.TaskEventRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TaskEventService(
    private val taskEventRepository: TaskEventRepository,
) {
    @Transactional
    fun save(replyEvent: TaskEvent): TaskEvent = taskEventRepository.save(TaskEventEntity.of(replyEvent)).toDomain()

    fun getById(id: Long): TaskEvent =
        taskEventRepository.findByIdOrNull(id)?.toDomain()
            ?: throw BusinessException(StatusCode.EVENT_NOT_FOUND)
}
