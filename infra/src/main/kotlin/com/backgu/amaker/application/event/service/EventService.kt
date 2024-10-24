package com.backgu.amaker.application.event.service

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.event.Event
import com.backgu.amaker.infra.jpa.event.repository.EventRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EventService(
    private val eventRepository: EventRepository,
) {
    fun getEventById(eventId: Long): Event =
        eventRepository.findByIdOrNull(eventId)?.toDomain() ?: throw BusinessException(StatusCode.EVENT_NOT_FOUND)

    fun findEventByIdsToMap(eventIds: List<Long>): Map<Long, Event> =
        eventRepository.findAllById(eventIds).map { it.toDomain() }.associateBy { it.id }

    fun findEventByWorkspaceId(workspaceId: Long): List<Event> = eventRepository.findAllByWorkspaceId(workspaceId).map { it.toDomain() }
}
