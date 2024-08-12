package com.backgu.amaker.infra.redis.chat.data

import com.backgu.amaker.domain.event.EventWithUser
import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

class EventWithUserCache(
    val id: Long,
    val eventTitle: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val users: List<String>,
    var finishedCount: Int,
    val totalAssignedCount: Int,
) {
    fun toDomain(users: List<User>) =
        EventWithUser(
            id = id,
            eventTitle = eventTitle,
            deadLine = deadLine,
            notificationStartTime = notificationStartTime,
            notificationInterval = notificationInterval,
            users = users,
            finishedCount = finishedCount,
            totalAssignedCount = totalAssignedCount,
        )

    companion object {
        fun of(eventWithUser: EventWithUser): EventWithUserCache =
            EventWithUserCache(
                id = eventWithUser.id,
                eventTitle = eventWithUser.eventTitle,
                deadLine = eventWithUser.deadLine,
                notificationStartTime = eventWithUser.notificationStartTime,
                notificationInterval = eventWithUser.notificationInterval,
                users = eventWithUser.users.map { it.id },
                finishedCount = eventWithUser.finishedCount,
                totalAssignedCount = eventWithUser.totalAssignedCount,
            )
    }

    fun updateFinishedCount(finishedCount: Int): EventWithUserCache {
        this.finishedCount = finishedCount
        return this
    }
}
