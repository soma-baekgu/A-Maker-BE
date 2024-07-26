package com.backgu.amaker.event.domain

import java.time.LocalDateTime

abstract class Event(
    val id: Long,
    val eventTitle: String,
    val eventDetails: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
