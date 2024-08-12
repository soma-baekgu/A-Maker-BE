package com.backgu.amaker.domain.event

import com.backgu.amaker.domain.user.User
import java.time.LocalDateTime

data class EventWithUser(
    val id: Long,
    val eventTitle: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val users: List<User>,
    val finishedCount: Int,
    val totalAssignedCount: Int,
)
