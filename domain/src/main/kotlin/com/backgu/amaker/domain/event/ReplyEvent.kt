package com.backgu.amaker.domain.event

import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

class ReplyEvent(
    id: Long,
    eventTitle: String,
    val eventDetails: String,
    deadLine: LocalDateTime,
    notificationStartTime: LocalDateTime,
    notificationInterval: Int,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
) : Event(id, eventTitle, deadLine, notificationStartTime, notificationInterval, createdAt, updatedAt) {
    companion object {
        const val EVENT_TYPE = "REPLY"
    }

    fun addReplyComment(
        user: User,
        content: String,
    ) = ReplyComment(
        userId = user.id,
        eventId = id,
        content = content,
    )

    override fun getEventType(): String = EVENT_TYPE
}
