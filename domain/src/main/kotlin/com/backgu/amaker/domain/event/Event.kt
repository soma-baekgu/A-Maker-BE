package com.backgu.amaker.domain.event

import java.time.LocalDateTime

abstract class Event(
    val id: Long,
    val eventTitle: String,
    val deadLine: LocalDateTime,
    val notificationStartTime: LocalDateTime,
    val notificationInterval: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
