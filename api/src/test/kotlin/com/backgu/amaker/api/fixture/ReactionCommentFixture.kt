package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.event.ReactionComment
import com.backgu.amaker.infra.jpa.event.entity.ReactionCommentEntity
import com.backgu.amaker.infra.jpa.event.repository.ReactionCommentRepository
import org.springframework.stereotype.Component

@Component
class ReactionCommentFixture(
    val reactionCommentRepository: ReactionCommentRepository,
) {
    fun createPersistedReactionComment(
        userId: String,
        eventId: Long,
        optionId: Long,
    ): ReactionComment =
        reactionCommentRepository
            .save(
                ReactionCommentEntity(
                    userId = userId,
                    eventId = eventId,
                    optionId = optionId,
                ),
            ).toDomain()
}
