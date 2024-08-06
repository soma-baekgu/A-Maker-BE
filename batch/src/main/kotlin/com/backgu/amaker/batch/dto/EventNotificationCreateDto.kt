package com.backgu.amaker.batch.dto

import java.time.LocalDateTime

data class EventNotificationCreateDto(
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
)
