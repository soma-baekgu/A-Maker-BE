package com.backgu.amaker.api.event.dto

import com.backgu.amaker.domain.event.TaskComment
import java.time.LocalDateTime

data class TaskCommentDto(
    val id: Long,
    val userId: String,
    val eventId: Long,
    val path: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun of(taskComment: TaskComment) =
            TaskCommentDto(
                id = taskComment.id,
                userId = taskComment.userId,
                eventId = taskComment.eventId,
                path = taskComment.path,
                createdAt = taskComment.createdAt,
                updatedAt = taskComment.updatedAt,
            )
    }
}
