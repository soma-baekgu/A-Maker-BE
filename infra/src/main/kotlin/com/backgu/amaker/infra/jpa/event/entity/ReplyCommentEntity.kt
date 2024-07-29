package com.backgu.amaker.infra.jpa.event.entity

import com.backgu.amaker.domain.event.ReplyComment
import com.backgu.amaker.infra.jpa.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "ReplyComment")
@Table(name = "reply_comment")
class ReplyCommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false)
    val eventId: Long,
    @Column(nullable = false)
    var content: String,
) : BaseTimeEntity() {
    fun toDomain(): ReplyComment =
        ReplyComment(
            id = id,
            userId = userId,
            eventId = eventId,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    companion object {
        fun of(replyComment: ReplyComment): ReplyCommentEntity =
            ReplyCommentEntity(
                id = replyComment.id,
                userId = replyComment.userId,
                eventId = replyComment.eventId,
                content = replyComment.content,
            )
    }
}
