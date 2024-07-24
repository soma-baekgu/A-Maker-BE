package com.backgu.amaker.event.service

import com.backgu.amaker.event.domain.ReplyEventAssignedUser
import com.backgu.amaker.event.jpa.ReplyEventAssignedUserEntity
import com.backgu.amaker.event.repository.ReplyEventAssignedRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReplyEventAssignedUserService(
    private val replyEventAssignedRepository: ReplyEventAssignedRepository,
) {
    @Transactional
    fun save(replyEventAssigned: ReplyEventAssignedUser): ReplyEventAssignedUser =
        replyEventAssignedRepository.save(ReplyEventAssignedUserEntity.of(replyEventAssigned)).toDomain()

    @Transactional
    fun saveAll(createAssignedUsers: List<ReplyEventAssignedUser>): List<ReplyEventAssignedUser> =
        replyEventAssignedRepository
            .saveAll(
                createAssignedUsers
                    .map { ReplyEventAssignedUserEntity.of(it) },
            ).map { it.toDomain() }
}
