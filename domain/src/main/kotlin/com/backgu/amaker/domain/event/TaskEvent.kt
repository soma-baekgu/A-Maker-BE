package com.backgu.amaker.domain.event

import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

class TaskEvent(
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
        const val EVENT_TYPE = "TASK"
    }

    override fun getEventType(): String = EVENT_TYPE

    fun addTaskComment(
        user: User,
        path: String,
    ): TaskComment =
        TaskComment(
            userId = user.id,
            eventId = id,
            path = path,
        )
}
