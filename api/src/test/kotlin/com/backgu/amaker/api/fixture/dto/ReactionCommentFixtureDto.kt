package com.backgu.amaker.api.fixture.dto

import com.backgu.amaker.domain.event.ReactionEvent
import com.backgu.amaker.domain.event.ReactionOption

data class ReactionCommentFixtureDto(
    val reactionEvent: ReactionEvent,
    val options: List<ReactionOption>,
)
