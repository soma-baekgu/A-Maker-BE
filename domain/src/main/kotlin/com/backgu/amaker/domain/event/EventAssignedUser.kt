package com.backgu.amaker.domain.event

import java.time.LocalDateTime

class EventAssignedUser(
    val id: Long = 0L,
    val eventId: Long,
    val userId: String,
    val isFinished: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
