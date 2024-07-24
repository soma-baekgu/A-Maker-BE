package com.backgu.amaker.event.domain

import java.time.LocalDateTime

class ReplyEventAssignedUser(
    val id: Long = 0L,
    val eventId: Long,
    val userId: String,
    val isFinished: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
