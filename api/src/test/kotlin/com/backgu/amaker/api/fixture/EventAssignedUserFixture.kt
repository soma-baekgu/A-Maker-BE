package com.backgu.amaker.api.fixture

import com.backgu.amaker.infra.jpa.event.entity.EventAssignedUserEntity
import com.backgu.amaker.infra.jpa.event.repository.EventAssignedUserRepository
import org.springframework.stereotype.Component

@Component
class EventAssignedUserFixture(
    val eventAssignedUserRepository: EventAssignedUserRepository,
) {
    fun createPersistedEventAssignedUser(
        userId: String = "test-user-id",
        eventId: Long = 1L,
    ) = eventAssignedUserRepository.save(
        EventAssignedUserEntity(
            userId = userId,
            eventId = eventId,
        ),
    )
}
