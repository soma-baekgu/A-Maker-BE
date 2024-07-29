package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.event.ReplyComment
import com.backgu.amaker.infra.jpa.event.entity.ReplyCommentEntity
import com.backgu.amaker.infra.jpa.event.repository.ReplyCommentRepository
import org.springframework.stereotype.Component

@Component
class ReplyCommentFixture(
    val replyCommentRepository: ReplyCommentRepository,
) {
    fun createPersistedReplyComment(
        userId: String,
        eventId: Long,
        content: String = "$userId $eventId 테스트 댓글",
    ): ReplyComment =
        replyCommentRepository
            .save(
                ReplyCommentEntity(
                    userId = userId,
                    eventId = eventId,
                    content = content,
                ),
            ).toDomain()
}
