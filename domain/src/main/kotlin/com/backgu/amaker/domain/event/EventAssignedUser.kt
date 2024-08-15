package com.backgu.amaker.domain.event

import java.time.LocalDateTime

class EventAssignedUser(
    val id: Long = 0L,
    val eventId: Long,
    val userId: String,
    var isFinished: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun updateIsFinished(isFinished: Boolean): EventAssignedUser {
        this.isFinished = isFinished
        return this
    }
}
