package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.event.ReactionOption
import com.backgu.amaker.infra.jpa.event.entity.ReactionOptionEntity
import com.backgu.amaker.infra.jpa.event.repository.ReactionOptionRepository
import org.springframework.stereotype.Component

@Component
class ReactionOptionFixture(
    val reactionOptionRepository: ReactionOptionRepository,
) {
    fun createPersistedReactionOptions(
        eventId: Long,
        options: List<String> = listOf("옵션1", "옵션2", "옵션3"),
    ): List<ReactionOption> =
        reactionOptionRepository
            .saveAll(
                options.map {
                    ReactionOptionEntity(
                        eventId = eventId,
                        content = it,
                    )
                },
            ).map { it.toDomain() }
}
