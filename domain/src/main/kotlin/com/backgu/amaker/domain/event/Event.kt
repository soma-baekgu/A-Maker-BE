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

    fun createEventWithUser(
        users: List<User>,
        finishedCount: Int,
        totalAssignedCount: Int,
    ): EventWithUser =
        EventWithUser(
            id,
            eventTitle,
            deadLine,
            notificationStartTime,
            notificationInterval,
            users,
            finishedCount,
            totalAssignedCount,
        )

    fun isAchieved(user: List<EventAssignedUser>): Boolean = user.size == user.count { it.isFinished }

    fun isClosed(): Boolean = LocalDateTime.now().isAfter(deadLine)
}
