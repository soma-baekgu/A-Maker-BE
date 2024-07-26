package com.backgu.amaker.infra.jpa.event.repository

import com.backgu.amaker.infra.jpa.event.entity.EventAssignedUserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventAssignedRepository : JpaRepository<EventAssignedUserEntity, Long> {
    fun findAllByEventId(eventId: Long): List<EventAssignedUserEntity>

    fun findByEventIdIn(eventIds: List<Long>): List<EventAssignedUserEntity>
}
