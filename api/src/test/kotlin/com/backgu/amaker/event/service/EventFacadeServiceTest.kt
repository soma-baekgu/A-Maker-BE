package com.backgu.amaker.event.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.event.dto.ReplyEventCreateDto
import com.backgu.amaker.fixture.EventFixtureFacade
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.test.Test

@DisplayName("EventFacadeService 테스트")
@Transactional
@SpringBootTest
class EventFacadeServiceTest {
    @Autowired
    lateinit var eventFacadeService: EventFacadeService

    companion object {
        private lateinit var fixtures: EventFixtureFacade
        private const val DEFAULT_USER_ID: String = "default-user"
        lateinit var chatRoom: ChatRoom

        @JvmStatic
        @BeforeAll
        fun setUp(
            @Autowired fixtures: EventFixtureFacade,
        ) {
            chatRoom = fixtures.setUp(DEFAULT_USER_ID)
            this.fixtures = fixtures
        }
    }

    @Test
    @DisplayName("reply 이벤트 생성 테스트")
    fun createReplyEvent() {
        // given

        val replyEventCreateDto =
            ReplyEventCreateDto(
                eventTitle = "eventTitle",
                deadLine = LocalDateTime.now().plusDays(1),
                notificationStartHour = 1,
                notificationStartMinute = 30,
                interval = 10,
                eventDetails = "eventDetails",
                assignees = listOf("$DEFAULT_USER_ID@email.com"),
            )

        // when
        val replyEvent =
            eventFacadeService.createReplyEvent(
                userId = DEFAULT_USER_ID,
                chatRoomId = chatRoom.id,
                replyEventCreateDto = replyEventCreateDto,
            )

        // then
        assertThat(replyEvent).isNotNull()
        assertThat(replyEvent.deadLine).isEqualTo(replyEventCreateDto.deadLine)
    }
}
