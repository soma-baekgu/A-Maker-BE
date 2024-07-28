package com.backgu.amaker.domain.event

import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

abstract class Event(
    val id: Long,
    val eventTitle: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    fun createAssignedUsers(userIds: List<User>): List<EventAssignedUser> = userIds.map { EventAssignedUser(eventId = id, userId = it.id) }
}
