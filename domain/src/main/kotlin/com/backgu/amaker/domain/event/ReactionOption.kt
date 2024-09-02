package com.backgu.amaker.domain.event

import java.time.LocalDateTime

class ReactionOption(
    val id: Long = 0L,
    val eventId: Long,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
