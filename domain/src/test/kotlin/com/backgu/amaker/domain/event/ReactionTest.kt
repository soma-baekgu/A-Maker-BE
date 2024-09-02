package com.backgu.amaker.domain.event

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import java.time.LocalDateTime
import kotlin.test.Test

@DisplayName("Reation 테스트")
class ReactionTest {
    @Test
    @DisplayName("reaction option 생성 테스트")
    fun createReactionOption() {
        // given
        val reactionEvent =
            ReactionEvent(
                id = 1,
                eventTitle = "eventTitle",
                deadLine = LocalDateTime.now().plusDays(1),
                notificationStartTime = LocalDateTime.now().plusDays(1),
                notificationInterval = 10,
            )
        val contents = listOf("content1", "content2")

        // when
        val reactionOptions = reactionEvent.createReactionOption(contents)

        // then
        reactionOptions.forEachIndexed { index, reactionOption ->
            assertThat(reactionOption.eventId).isEqualTo(reactionEvent.id)
            assertThat(reactionOption.content).isEqualTo(contents[index])
        }
    }
}
