package com.backgu.amaker.chat.config

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.lang.Nullable
import org.springframework.messaging.converter.StringMessageConverter
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import kotlin.test.Test

@DisplayName("웹소켓 설정 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketConfigTest {
    var stompClient: WebSocketStompClient = WebSocketStompClient(StandardWebSocketClient())

    @LocalServerPort
    var port: Int = 0

    @Test
    @DisplayName("웹소켓 연결 테스트")
    fun testWebSocketConnection() {
        // given

        // when
        val connectFuture: CompletableFuture<StompSession> =
            stompClient.connectAsync("ws://localhost:$port/ws", stompHandler)
        val session: StompSession = connectFuture.get(1, TimeUnit.SECONDS)

        // then
        assertThat(session.isConnected).isTrue()
    }

    @BeforeEach
    fun setup() {
        val webSocketClient = StandardWebSocketClient()
        stompClient = WebSocketStompClient(webSocketClient)
        stompClient.messageConverter = StringMessageConverter()
    }

    private val stompHandler: StompSessionHandler =
        object : StompSessionHandlerAdapter() {
            override fun handleException(
                session: StompSession,
                @Nullable command: StompCommand?,
                headers: StompHeaders,
                payload: ByteArray,
                exception: Throwable,
            ) = throw exception

            override fun handleTransportError(
                session: StompSession,
                exception: Throwable,
            ): Unit = throw exception
        }
}
