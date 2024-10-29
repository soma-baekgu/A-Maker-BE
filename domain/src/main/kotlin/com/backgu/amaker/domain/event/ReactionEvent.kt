package com.backgu.amaker.domain.event

import java.time.LocalDateTime

class ReactionEvent(
    id: Long,
    eventTitle: String,
    deadLine: LocalDateTime,
    notificationStartTime: LocalDateTime,
    notificationInterval: Int,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
) : Event(id, eventTitle, deadLine, notificationStartTime, notificationInterval, createdAt, updatedAt) {
    companion object {
        const val EVENT_TYPE = "REACTION"
    }

    fun createReactionOption(contents: List<String>) =
        contents.map {
            ReactionOption(
                eventId = id,
                content = it,
            )
        }

    fun createReactionComment(
        userId: String,
        optionId: Long,
    ) = ReactionComment(
        eventId = id,
        userId = userId,
        optionId = optionId,
    )

    override fun getEventType(): String = EVENT_TYPE
}
