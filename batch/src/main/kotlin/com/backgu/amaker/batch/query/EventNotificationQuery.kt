package com.backgu.amaker.batch.query

import java.time.LocalDateTime

data class EventNotificationQuery(
    val eventAssignedUserId: Long,
    val userId: String,
    val eventId: Long,
    val eventTitle: String,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val deadline: LocalDateTime,
    val notificationId: Long?,
    val notificationCreatedAt: LocalDateTime?,
    val email: String,
) {
    fun isAfter(now: LocalDateTime): Boolean = this.notificationStartTime.isAfter(now)

    fun shouldSendNotification(now: LocalDateTime): Boolean =
        this.notificationId == null ||
            this.notificationCreatedAt
                ?.plusMinutes(this.notificationInterval.toLong())
                ?.isBefore(now) ?: false
}
