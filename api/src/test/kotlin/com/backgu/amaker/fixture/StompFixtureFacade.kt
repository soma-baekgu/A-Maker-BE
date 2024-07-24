package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.dto.request.ChatCreateRequest
import com.backgu.amaker.chat.dto.response.ChatWithUserResponse
import com.backgu.amaker.security.jwt.component.JwtComponent
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.lang.Nullable
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.util.concurrent.CompletableFuture

@Component
class StompFixtureFacade(
    private val chatFixtureFacade: ChatFixtureFacade,
    private val jwtComponent: JwtComponent,
) {
    val stompClient: WebSocketStompClient =
        WebSocketStompClient(StandardWebSocketClient()).apply {
            val messageConverter = MappingJackson2MessageConverter()
            this.messageConverter = messageConverter
            val objectMapper = messageConverter.objectMapper
            objectMapper.registerModules(JavaTimeModule(), ParameterNamesModule())
        }

    fun setup(
        userId: String = "test-user-id",
        name: String = "김리더",
        workspaceName: String = "테스트 워크스페이스",
    ): ChatRoom =
        chatFixtureFacade.setUp(
            userId = userId,
            name = name,
            workspaceName = workspaceName,
        )

    fun createStompHeaders(userId: String): StompHeaders =
        StompHeaders().apply {
            add("Authorization", "Bearer ${jwtComponent.create(userId, "ROLE_USER")}")
        }

    fun connectToWebSocket(
        port: Int,
        headers: StompHeaders,
    ): CompletableFuture<StompSession> =
        stompClient.connectAsync(
            "ws://localhost:$port/ws",
            WebSocketHttpHeaders(),
            headers,
            stompHandler,
        )

    fun subscribeToChatRoom(
        session: StompSession,
        chatRoomId: Long,
    ): CompletableFuture<ChatWithUserResponse> {
        val future: CompletableFuture<ChatWithUserResponse> = CompletableFuture()
        session.subscribe(
            "/sub/chat-rooms/$chatRoomId",
            object : StompFrameHandler {
                override fun getPayloadType(headers: StompHeaders) = ChatWithUserResponse::class.java

                override fun handleFrame(
                    headers: StompHeaders,
                    payload: Any?,
                ) {
                    future.complete(payload as ChatWithUserResponse)
                }
            },
        )
        return future
    }

    fun sendMessage(
        session: StompSession,
        destination: String,
        request: ChatCreateRequest,
    ) {
        session.send(destination, request)
    }

    val stompHandler: StompSessionHandler =
        object : StompSessionHandlerAdapter() {
            override fun handleException(
                session: StompSession,
                @Nullable command: StompCommand?,
                headers: StompHeaders,
                payload: ByteArray,
                exception: Throwable,
            ): Unit = throw exception

            override fun handleTransportError(
                session: StompSession,
                exception: Throwable,
            ): Unit = throw exception
        }
}
