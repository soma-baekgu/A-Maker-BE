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
    fun createAssignedUsers(userIds: List<User>): List<EventAssignedUser> = userIds.map { EventAssignedUser(eventId = id, userId = it.id) }
}
