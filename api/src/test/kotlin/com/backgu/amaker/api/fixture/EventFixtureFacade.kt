package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.chat.ChatRoom
import org.springframework.stereotype.Component

@Component
class EventFixtureFacade(
    val chatFixtureFacade: ChatFixtureFacade,
    val replyEventFixture: ReplyEventFixture,
    val reactionEventFixture: ReactionEventFixture,
    val reactionOptionFixture: ReactionOptionFixture,
    val eventAssignedUserFixture: EventAssignedUserFixture,
) {
    fun setUp(
        userId: String = "test-user-id",
        name: String = "김리더",
        email: String = "$userId@email.com",
        workspaceName: String = "테스트 워크스페이스",
    ): ChatRoom =
        chatFixtureFacade.setUp(
            userId = userId,
            name = name,
            email = email,
            workspaceName = workspaceName,
        )

    fun deleteAll() {
        chatFixtureFacade.deleteAll()
    }
}
