package com.backgu.amaker.event.domain

import com.backgu.amaker.user.domain.User
import java.time.LocalDateTime

class ReplyEvent(
    id: Long,
    eventTitle: String,
    eventDetails: String,
    deadLine: LocalDateTime,
    notificationStartTime: LocalDateTime,
    notificationInterval: Int,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
) : Event(id, eventTitle, eventDetails, deadLine, notificationStartTime, notificationInterval, createdAt, updatedAt) {
    fun createAssignedUsers(userIds: List<User>): List<EventAssignedUser> = userIds.map { EventAssignedUser(eventId = id, userId = it.id) }
}
