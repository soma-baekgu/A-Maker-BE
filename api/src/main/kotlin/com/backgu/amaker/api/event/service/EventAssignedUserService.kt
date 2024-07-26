package com.backgu.amaker.api.event.service

import com.backgu.amaker.domain.event.EventAssignedUser
import com.backgu.amaker.infra.jpa.event.entity.EventAssignedUserEntity
import com.backgu.amaker.infra.jpa.event.repository.EventAssignedRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EventAssignedUserService(
    private val eventAssignedRepository: EventAssignedRepository,
) {
    @Transactional
    fun save(eventAssigned: EventAssignedUser): EventAssignedUser =
        eventAssignedRepository.save(EventAssignedUserEntity.of(eventAssigned)).toDomain()

    fun findAllByEventId(eventId: Long): List<EventAssignedUser> = eventAssignedRepository.findAllByEventId(eventId).map { it.toDomain() }

    fun findByEventIdsToEventIdMapped(eventIds: List<Long>): Map<Long, List<EventAssignedUser>> =
        eventAssignedRepository.findByEventIdIn(eventIds).map { it.toDomain() }.groupBy { it.eventId }

    @Transactional
    fun saveAll(createAssignedUsers: List<EventAssignedUser>): List<EventAssignedUser> =
        eventAssignedRepository
            .saveAll(
                createAssignedUsers
                    .map { EventAssignedUserEntity.of(it) },
            ).map { it.toDomain() }
}
