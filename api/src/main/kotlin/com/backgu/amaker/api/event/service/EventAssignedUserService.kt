package com.backgu.amaker.api.event.service

import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.event.Event
import com.backgu.amaker.domain.event.EventAssignedUser
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.infra.jpa.event.entity.EventAssignedUserEntity
import com.backgu.amaker.infra.jpa.event.repository.EventAssignedUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EventAssignedUserService(
    private val eventAssignedUserRepository: EventAssignedUserRepository,
) {
    @Transactional
    fun save(eventAssigned: EventAssignedUser): EventAssignedUser =
        eventAssignedUserRepository.save(EventAssignedUserEntity.of(eventAssigned)).toDomain()

    fun findAllByEventId(eventId: Long): List<EventAssignedUser> = eventAssignedUserRepository.findAllByEventId(eventId).map { it.toDomain() }

    fun findByEventIdsToEventIdMapped(eventIds: List<Long>): Map<Long, List<EventAssignedUser>> =
        eventAssignedUserRepository.findByEventIdIn(eventIds).map { it.toDomain() }.groupBy { it.eventId }

    @Transactional
    fun saveAll(createAssignedUsers: List<EventAssignedUser>): List<EventAssignedUser> =
        eventAssignedUserRepository
            .saveAll(
                createAssignedUsers
                    .map { EventAssignedUserEntity.of(it) },
            ).map { it.toDomain() }

    fun getByUserAndEvent(
        user: User,
        event: Event,
    ): EventAssignedUser =
        eventAssignedUserRepository.findByUserIdAndEventId(user.id, event.id)?.toDomain()
            ?: throw BusinessException(StatusCode.EVENT_ASSIGNED_USER_NOT_FOUND)
}
