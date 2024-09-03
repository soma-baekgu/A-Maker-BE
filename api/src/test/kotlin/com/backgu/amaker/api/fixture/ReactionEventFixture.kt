package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.event.ReactionEvent
import com.backgu.amaker.infra.jpa.event.entity.ReactionEventEntity
import com.backgu.amaker.infra.jpa.event.repository.ReactionEventRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ReactionEventFixture(
    val reactionEventRepository: ReactionEventRepository,
) {
    fun createPersistedReactionEvent(
        id: Long,
        eventTitle: String = "$id 번째 이벤트",
        deadLine: LocalDateTime = LocalDateTime.now(),
        notificationStartTime: LocalDateTime = LocalDateTime.now(),
        notificationInterval: Int = id.toInt(),
    ): ReactionEvent =
        reactionEventRepository
            .save(
                ReactionEventEntity(
                    id = id,
                    eventTitle = eventTitle,
                    deadLine = deadLine,
                    notificationStartTime = notificationStartTime,
                    notificationInterval = notificationInterval,
                ),
            ).toDomain()
}
