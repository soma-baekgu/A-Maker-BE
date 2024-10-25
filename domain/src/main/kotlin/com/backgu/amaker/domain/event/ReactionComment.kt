package com.backgu.amaker.domain.event

import java.time.LocalDateTime

class ReactionComment(
    val id: Long = 0L,
    val userId: String,
    val eventId: Long,
    var optionId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun toggleOptionId(optionId: Long) {
        this.optionId = optionId
    }
}
