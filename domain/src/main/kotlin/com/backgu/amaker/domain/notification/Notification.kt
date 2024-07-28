package com.backgu.amaker.domain.notification

import java.time.LocalDateTime

class Notification(
    id: Long,
    title: String,
    content: String,
    userId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
