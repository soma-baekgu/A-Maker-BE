package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.dto.ChatDto
import com.backgu.amaker.chat.dto.GeneralChatCreateDto
import com.backgu.amaker.fixture.ChatFixtureFacade
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("ChatFacadeService 테스트")
@Transactional
@SpringBootTest
class ChatFacadeServiceTest {
    @Autowired
    lateinit var chatFacadeService: ChatFacadeService

    companion object {
        private lateinit var fixture: ChatFixtureFacade
        private const val DEFAULT_USER_ID: String = "default-user"

        @JvmStatic
        @BeforeAll
        fun setUp(
            @Autowired fixture: ChatFixtureFacade,
        ) {
            fixture.setUp()
            this.fixture = fixture
        }
    }

    @Test
    @DisplayName("일반 채팅 생성 테스트")
    fun createGeneralChat() {
        // given
        val chatRoom: ChatRoom =
            fixture.setUp(
                userId = DEFAULT_USER_ID,
            )

        val generalChatCreateDto =
            GeneralChatCreateDto(
                content = "content",
            )

        // when
        val chatDto: ChatDto =
            chatFacadeService.createGeneralChat(
                generalChatCreateDto = generalChatCreateDto,
                chatRoomId = chatRoom.id,
                userId = DEFAULT_USER_ID,
            )

        // then
        assertThat(chatDto.userId).isEqualTo(DEFAULT_USER_ID)
        assertThat(chatDto.chatRoomId).isEqualTo(chatRoom.id)
        assertThat(chatDto.content).isEqualTo(generalChatCreateDto.content)
    }
}
