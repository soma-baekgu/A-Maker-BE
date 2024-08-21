package com.backgu.amaker.tester.setup.service

import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.tester.setup.fixture.ChatFixtureFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatSetupService(
    private val chatFixtureFacade: ChatFixtureFacade,
) {
    @Transactional
    fun chatSetup(): ChatRoom = chatFixtureFacade.setUp()
}
