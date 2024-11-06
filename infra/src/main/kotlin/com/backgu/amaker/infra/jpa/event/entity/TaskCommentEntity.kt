package com.backgu.amaker.infra.jpa.event.entity

import com.backgu.amaker.domain.event.TaskComment
import com.backgu.amaker.infra.jpa.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "TaskComment")
@Table(name = "task_comment")
class TaskCommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false)
    val eventId: Long,
    @Column(nullable = false)
    var path: String,
) : BaseTimeEntity() {
    fun toDomain(): TaskComment =
        TaskComment(
            id = id,
            userId = userId,
            eventId = eventId,
            path = path,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    companion object {
        fun of(taskComment: TaskComment): TaskCommentEntity =
            TaskCommentEntity(
                id = taskComment.id,
                userId = taskComment.userId,
                eventId = taskComment.eventId,
                path = taskComment.path,
            )
    }
}
