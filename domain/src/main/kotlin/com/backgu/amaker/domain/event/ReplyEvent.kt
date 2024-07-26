package com.backgu.amaker.domain.event

import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

class ReplyEvent(
    val id: Long,
    val eventTitle: String,
    val eventDetails: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun createAssignedUsers(userIds: List<User>): List<EventAssignedUser> = userIds.map { EventAssignedUser(eventId = id, userId = it.id) }
}
