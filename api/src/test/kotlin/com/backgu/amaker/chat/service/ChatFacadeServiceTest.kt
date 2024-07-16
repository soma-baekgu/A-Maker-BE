package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.Chat
import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.dto.ChatDto
import com.backgu.amaker.chat.dto.ChatListDto
import com.backgu.amaker.chat.dto.ChatQuery
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
        assertThat(chatDto.user.id).isEqualTo(DEFAULT_USER_ID)
        assertThat(chatDto.chatRoomId).isEqualTo(chatRoom.id)
        assertThat(chatDto.content).isEqualTo(generalChatCreateDto.content)
    }

    @Test
    @DisplayName("이전 채팅 조회 테스트")
    fun getPreviousChatTest() {
        // given
        val userId = "test-user-id"
        val chatRoom: ChatRoom = fixture.setUp(userId = userId)
        val prevChats: List<Chat> = fixture.chat.createPersistedChats(chatRoom.id, userId, 10)
        val currentChat: Chat = fixture.chat.createPersistedChat(chatRoom.id, userId, "현재 테스트 메시지")

        // when
        val previousChat: ChatListDto =
            chatFacadeService.getPreviousChat(userId, ChatQuery(currentChat.id, chatRoom.id, 10))

        // then
        assertThat(previousChat.chatList).hasSize(10)
        assertThat(previousChat.size).isEqualTo(previousChat.chatList.size)
        assertThat(previousChat.cursor).isNotEqualTo(prevChats.last().id)
    }

    @Test
    @DisplayName("이전 채팅 조회 테스트 - 이전 채팅이 요청한 개수보다 적은 경우")
    fun getPreviousChatTestWhenPrevChatSizeLessThanRequestedSize() {
        // given
        val userId = "test-user-id"
        val chatRoom: ChatRoom = fixture.setUp(userId = userId)
        fixture.chat.deleteAll()

        val prevChats: List<Chat> = fixture.chat.createPersistedChats(chatRoom.id, userId, 5)
        val currentChat: Chat = fixture.chat.createPersistedChat(chatRoom.id, userId, "현재 테스트 메시지")

        // when
        val previousChat: ChatListDto =
            chatFacadeService.getPreviousChat(userId, ChatQuery(currentChat.id, chatRoom.id, 10))

        // then
        assertThat(previousChat.chatList).hasSize(5)
        assertThat(previousChat.size).isEqualTo(previousChat.chatList.size)
        assertThat(previousChat.cursor).isNotEqualTo(prevChats.last().id)
    }

    @Test
    @DisplayName("이전 채팅 조회 테스트 - 이전 채팅이 없는 경우")
    fun getPreviousChatTestWhenNoPrevChat() {
        // given
        val userId = "test-user-id"
        val chatRoom: ChatRoom = fixture.setUp(userId = userId)
        fixture.chat.deleteAll()

        val currentChat: Chat = fixture.chat.createPersistedChat(chatRoom.id, userId, "현재 테스트 메시지")

        // when
        val previousChat: ChatListDto =
            chatFacadeService.getPreviousChat(userId, ChatQuery(currentChat.id, chatRoom.id, 10))

        // then
        assertThat(previousChat.chatList).isEmpty()
        assertThat(previousChat.size).isEqualTo(previousChat.chatList.size)
        assertThat(previousChat.cursor).isEqualTo(currentChat.id)
    }

    @Test
    @DisplayName("이후 채팅 조회 테스트")
    fun getAfterChatTest() {
        // given
        val userId = "test-user-id"
        val chatRoom: ChatRoom = fixture.setUp(userId = userId)
        val prevChats = fixture.chat.createPersistedChats(chatRoom.id, userId, 10)
        val currentChat: Chat = fixture.chat.createPersistedChat(chatRoom.id, userId, "현재 테스트 메시지")
        val afterChats = fixture.chat.createPersistedChats(chatRoom.id, userId, 30)

        // when
        val findAfterChats: ChatListDto =
            chatFacadeService.getAfterChat(userId, ChatQuery(currentChat.id, chatRoom.id, 10))

        // then
        assertThat(findAfterChats.chatList).hasSize(10)
        assertThat(findAfterChats.size).isEqualTo(findAfterChats.chatList.size)
        assertThat(findAfterChats.cursor).isNotEqualTo(prevChats.last().id)
    }

    @Test
    @DisplayName("이전 채팅 조회 테스트")
    fun getAfterChatTestWhenNoAfterChat() {
        // given
        val userId = "test-user-id"
        val chatRoom: ChatRoom = fixture.setUp(userId = userId)
        val prevChats: List<Chat> = fixture.chat.createPersistedChats(chatRoom.id, userId, 30)
        val currentChat: Chat = fixture.chat.createPersistedChat(chatRoom.id, userId, "현재 테스트 메시지")
        val afterChats = fixture.chat.createPersistedChats(chatRoom.id, userId, 10)

        // when
        val findPrevChats: ChatListDto =
            chatFacadeService.getAfterChat(userId, ChatQuery(currentChat.id, chatRoom.id, 10))

        // then
        assertThat(findPrevChats.chatList).hasSize(10)
        assertThat(findPrevChats.cursor).isNotEqualTo(prevChats.last().id)
    }
}
