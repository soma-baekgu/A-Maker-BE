package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.ChatRoom
import org.eclipse.jdt.internal.compiler.parser.Parser.name
import org.springframework.stereotype.Component

@Component
class EventFixtureFacade(
    val chatFixtureFacade: ChatFixtureFacade,
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