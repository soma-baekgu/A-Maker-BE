package com.backgu.amaker.api.event.dto

import java.time.LocalDateTime

data class ReactionEventCreateDto(
    val eventTitle: String,
    val options: List<String>,
    val assignees: List<String>,
    val deadLine: LocalDateTime,
    val notificationStartHour: Int,
    val notificationStartMinute: Int,
    val interval: Int,
)
