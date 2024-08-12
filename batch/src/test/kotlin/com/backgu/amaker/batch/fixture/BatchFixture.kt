package com.backgu.amaker.batch.fixture

import com.backgu.amaker.infra.jpa.event.entity.EventAssignedUserEntity
import com.backgu.amaker.infra.jpa.event.entity.ReplyEventEntity
import com.backgu.amaker.infra.jpa.event.repository.EventAssignedUserRepository
import com.backgu.amaker.infra.jpa.event.repository.ReplyEventRepository
import com.backgu.amaker.infra.jpa.notification.entity.EventNotificationEntity
import com.backgu.amaker.infra.jpa.notification.repository.EventNotificationRepository
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class BatchFixture(
    val userFixture: UserFixture,
    val eventAssignedUserRepository: EventAssignedUserRepository,
    val replyEventRepository: ReplyEventRepository,
    val eventNotificationRepository: EventNotificationRepository,
) {
    fun setup() {
        val users = userFixture.createPersistedUsers(10)

        val events =
            (1..10).map {
                replyEventRepository.save(
                    ReplyEventEntity(
                        id = it.toLong(),
                        eventTitle = "event-$it",
                        deadLine = LocalDateTime.now().plusDays(1),
                        notificationStartTime = LocalDateTime.now(),
                        notificationInterval = 10,
                        eventDetails = "event-details-$it",
                    ),
                )
            }

        eventAssignedUserRepository.saveAll(
            events.flatMapIndexed { index, event ->
                users.take(index + 1).map { user ->

                    EventAssignedUserEntity(
                        eventId = event.id,
                        userId = user.id,
                        isFinished = index % 2 != 0,
                    )
                }
            },
        )

        eventNotificationRepository.save(
            EventNotificationEntity(
                title = "notification-title",
                content = "notification-content",
                userId = users.first().id,
                eventId = events.first().id,
            ),
        )
    }

    fun deleteAll() {
        eventAssignedUserRepository.deleteAll()
        replyEventRepository.deleteAll()
        eventNotificationRepository.deleteAll()
    }
}
