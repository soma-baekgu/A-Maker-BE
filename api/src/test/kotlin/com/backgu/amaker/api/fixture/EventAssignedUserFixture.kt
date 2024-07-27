package com.backgu.amaker.api.fixture

import com.backgu.amaker.infra.jpa.event.entity.EventAssignedUserEntity
import com.backgu.amaker.infra.jpa.event.repository.EventAssignedRepository
import org.springframework.stereotype.Component

@Component
class EventAssignedUserFixture(
    val eventAssignedRepository: EventAssignedRepository,
) {
    fun createPersistedEventAssignedUser(
        userId: String = "test-user-id",
        eventId: Long = 1L,
    ) = eventAssignedRepository.save(
        EventAssignedUserEntity(
            userId = userId,
            eventId = eventId,
        ),
    )
}
