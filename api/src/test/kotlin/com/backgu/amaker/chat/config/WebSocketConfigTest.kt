package com.backgu.amaker.chat.config

import com.backgu.amaker.fixture.StompFixtureFacade
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import java.util.concurrent.TimeUnit
import kotlin.test.Test

@DisplayName("웹소켓 설정 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebSocketConfigTest {
    @Autowired
    lateinit var fixtures: StompFixtureFacade

    @LocalServerPort
    var port: Int = 0

    @BeforeEach
    fun setup() {
        fixtures.setup()
    }

    @Test
    @DisplayName("웹소켓 연결 테스트")
    fun testWebSocketConnection() {
        // given
        val userId = "test-user-id"
        val headers = fixtures.createStompHeaders(userId)

        // when
        val connectFuture = fixtures.connectToWebSocket(port, headers)
        val session = connectFuture.get(1, TimeUnit.SECONDS)

        // then
        assertThat(session.isConnected).isTrue()
    }
}
