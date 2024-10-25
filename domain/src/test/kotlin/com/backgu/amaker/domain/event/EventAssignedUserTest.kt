package com.backgu.amaker.domain.event

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

@DisplayName("EventAssignedUser 테스트")
class EventAssignedUserTest {
    @Test
    @DisplayName("isFinished 업데이트 테스트")
    fun updateIsAchieved() {
        // given
        val eventAssignedUser =
            EventAssignedUser(
                id = 1L,
                eventId = 1L,
                userId = "test-user-id",
            )

        // when
        val updatedEventAssignedUser = eventAssignedUser.updateIsFinished(true)

        // then
        assertThat(updatedEventAssignedUser.isFinished).isTrue()
    }
}
