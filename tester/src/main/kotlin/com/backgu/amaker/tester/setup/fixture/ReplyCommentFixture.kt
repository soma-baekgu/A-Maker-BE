package com.backgu.amaker.tester.setup.fixture

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

    fun createPersistedReplyComments(
        userId: String,
        eventId: Long,
        count: Int = 100,
    ): List<ReplyComment> {
        val replyCommentEntities =
            (0 until count).map {
                ReplyCommentEntity(
                    eventId = eventId,
                    userId = userId,
                    content = "content $it",
                )
            }
        return replyCommentRepository.saveAll(replyCommentEntities).map { it.toDomain() }
    }
}
