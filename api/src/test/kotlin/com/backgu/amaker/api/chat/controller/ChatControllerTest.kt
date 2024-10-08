package com.backgu.amaker.api.chat.controller

import com.backgu.amaker.api.chat.dto.request.ChatCreateRequest
import com.backgu.amaker.api.chat.dto.response.DefaultChatWithUserResponse
import com.backgu.amaker.api.common.container.IntegrationTest
import com.backgu.amaker.api.fixture.StompFixtureFacade
import com.backgu.amaker.domain.chat.ChatRoom
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import kotlin.test.Test

@DisplayName("ChatController 테스트")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatControllerTest : IntegrationTest() {
    @Autowired
    lateinit var fixtures: StompFixtureFacade

    @LocalServerPort
    var port: Int = 0

    companion object {
        private lateinit var fixture: StompFixtureFacade
        private const val DEFAULT_USER_ID: String = "default-user-id"
        private lateinit var chatRoom: ChatRoom

        @JvmStatic
        @BeforeAll
        fun setUp(
            @Autowired fixture: StompFixtureFacade,
        ) {
            chatRoom = fixture.setup(DEFAULT_USER_ID)
            this.fixture = fixture
        }
    }

    @Test
    @DisplayName("일반 채팅 전송 테스트")
    fun sendGeneralChat() {
        // given
        val stompHeaders: StompHeaders = fixtures.createStompHeaders(DEFAULT_USER_ID)
        val connectFuture: CompletableFuture<StompSession> =
            fixtures.connectToWebSocket(port, stompHeaders)

        val stompSession: StompSession = connectFuture.get(1, TimeUnit.SECONDS)
        val subscribeToChatRoom: CompletableFuture<DefaultChatWithUserResponse> =
            fixtures.subscribeToChatRoom(stompSession, chatRoom.id)
        val content = "Hello World"
        val generalChatCreateRequest = ChatCreateRequest(content = content)

        // when
        fixtures.sendMessage(stompSession, "/pub/chat-rooms/${chatRoom.id}/general", generalChatCreateRequest)

        // then
        val chatWithUserResponse: DefaultChatWithUserResponse? = subscribeToChatRoom.get(5, TimeUnit.SECONDS)

        assertThat(chatWithUserResponse).isNotNull
        assertThat(chatWithUserResponse?.content).isEqualTo(content)
    }
}
