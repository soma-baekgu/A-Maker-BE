package com.backgu.amaker.api.event.service

import com.backgu.amaker.api.event.dto.ReplyEventCreateDto
import com.backgu.amaker.api.fixture.EventFixtureFacade
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatType
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

    @Test
    @DisplayName("reply 이벤트 조회 테스트")
    fun getReplyEvent() {
        // given
        val anotherUser = "another-user"
        val chat =
            fixtures.chatFixtureFacade.chatFixture.createPersistedChat(
                chatRoomId = chatRoom.id,
                userId = DEFAULT_USER_ID,
                chatType = ChatType.REPLY,
            )
        val replyEvent = fixtures.replyEventFixture.createPersistedReplyEvent(chat.id)
        fixtures.eventAssignedUserFixture.createPersistedEventAssignedUser(DEFAULT_USER_ID, replyEvent.id)
        fixtures.chatFixtureFacade.userFixture.createPersistedUser(anotherUser)
        fixtures.eventAssignedUserFixture.createPersistedEventAssignedUser(anotherUser, replyEvent.id)

        // when
        val result = eventFacadeService.getReplyEvent(DEFAULT_USER_ID, chatRoom.id, replyEvent.id)

        // then
        assertThat(result).isNotNull()
        assertThat(result.id).isEqualTo(chat.id)
        assertThat(result.waitingUser.size).isEqualTo(2)
        assertThat(result.eventCreator.id).isEqualTo(DEFAULT_USER_ID)
    }

    @Test
    @DisplayName("reply 이벤트 조회 테스트 - 다른 유저가 생성")
    fun getReplyEventCreateAnotherUser() {
        // given
        val anotherUser = "another-user"
        fixtures.chatFixtureFacade.userFixture.createPersistedUser(anotherUser)
        val chat =
            fixtures.chatFixtureFacade.chatFixture.createPersistedChat(
                chatRoomId = chatRoom.id,
                userId = anotherUser,
                chatType = ChatType.REPLY,
            )
        val replyEvent = fixtures.replyEventFixture.createPersistedReplyEvent(chat.id)
        fixtures.eventAssignedUserFixture.createPersistedEventAssignedUser(DEFAULT_USER_ID, replyEvent.id)
        fixtures.eventAssignedUserFixture.createPersistedEventAssignedUser(anotherUser, replyEvent.id)

        // when
        val result = eventFacadeService.getReplyEvent(DEFAULT_USER_ID, chatRoom.id, replyEvent.id)

        // then
        assertThat(result).isNotNull()
        assertThat(result.id).isEqualTo(chat.id)
        assertThat(result.waitingUser.size).isEqualTo(2)
        assertThat(result.eventCreator.id).isEqualTo(anotherUser)
    }
}
