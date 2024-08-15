package com.backgu.amaker.domain.event

import java.time.LocalDateTime

class ReplyComment(
    val id: Long = 0L,
    val userId: String,
    val eventId: Long,
    var content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
