package com.backgu.amaker.event.service

import com.backgu.amaker.event.domain.EventAssignedUser
import com.backgu.amaker.event.jpa.EventAssignedUserEntity
import com.backgu.amaker.event.repository.ReplyEventAssignedRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReplyEventAssignedUserService(
    private val replyEventAssignedRepository: ReplyEventAssignedRepository,
) {
    @Transactional
    fun save(replyEventAssigned: EventAssignedUser): EventAssignedUser =
        replyEventAssignedRepository.save(EventAssignedUserEntity.of(replyEventAssigned)).toDomain()

    @Transactional
    fun saveAll(createAssignedUsers: List<EventAssignedUser>): List<EventAssignedUser> =
        replyEventAssignedRepository
            .saveAll(
                createAssignedUsers
                    .map { EventAssignedUserEntity.of(it) },
            ).map { it.toDomain() }
}
