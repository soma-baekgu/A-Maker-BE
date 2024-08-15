package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.event.ReplyEvent
import com.backgu.amaker.infra.jpa.event.entity.ReplyEventEntity
import com.backgu.amaker.infra.jpa.event.repository.ReplyEventRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ReplyEventFixture(
    val replyEventRepository: ReplyEventRepository,
) {
    fun createPersistedReplyEvent(
        id: Long,
        eventTitle: String = "$id 번째 이벤트",
        deadLine: LocalDateTime = LocalDateTime.now(),
        notificationStartTime: LocalDateTime = LocalDateTime.now(),
        notificationInterval: Int = id.toInt(),
        eventDetails: String = "$id 번째 이벤트 상세 내용",
    ): ReplyEvent =
        replyEventRepository
            .save(
                ReplyEventEntity(
                    id = id,
                    eventTitle = eventTitle,
                    deadLine = deadLine,
                    notificationStartTime = notificationStartTime,
                    notificationInterval = notificationInterval,
                    eventDetails = eventDetails,
                ),
            ).toDomain()
}
