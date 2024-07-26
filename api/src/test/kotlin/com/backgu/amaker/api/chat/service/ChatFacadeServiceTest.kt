package com.backgu.amaker.api.chat.service

import com.backgu.amaker.api.chat.dto.ChatCreateDto
import com.backgu.amaker.api.chat.dto.ChatListDto
import com.backgu.amaker.api.chat.dto.ChatQuery
import com.backgu.amaker.api.chat.dto.ChatWithUserDto
import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.api.common.exception.StatusCode
import com.backgu.amaker.api.fixture.ChatFixtureFacade
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatRoomType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
    fun createChat() {
        // given
        val chatRoom: ChatRoom =
            fixture.setUp(
                userId = DEFAULT_USER_ID,
            )

        val chatCreateDto =
            ChatCreateDto(
                content = "content",
            )

        // when
        val chatDto: ChatWithUserDto =
            chatFacadeService.createChat(
                chatCreateDto = chatCreateDto,
                chatRoomId = chatRoom.id,
                userId = DEFAULT_USER_ID,
            )

        // then
        assertThat(chatDto.user.id).isEqualTo(DEFAULT_USER_ID)
        assertThat(chatDto.chatRoomId).isEqualTo(chatRoom.id)
        assertThat(chatDto.content).isEqualTo(chatCreateDto.content)
    }

    @Test
    @DisplayName("이전 채팅 조회 테스트")
    fun getPreviousChatTest() {
        // given
        val userId = "test-user-id"
        val chatRoom: ChatRoom = fixture.setUp(userId = userId)
        val prevChats: List<Chat> = fixture.chatFixture.createPersistedChats(chatRoom.id, userId, 10)
        val currentChat: Chat = fixture.chatFixture.createPersistedChat(chatRoom.id, userId, "현재 테스트 메시지")

        // when
        val previousChat: ChatListDto =
            chatFacadeService.getPreviousChat(
                userId,
                ChatQuery(currentChat.id, chatRoom.id, 10),
            )

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
        fixture.chatFixture.deleteAll()

        val prevChats: List<Chat> = fixture.chatFixture.createPersistedChats(chatRoom.id, userId, 5)
        val currentChat: Chat = fixture.chatFixture.createPersistedChat(chatRoom.id, userId, "현재 테스트 메시지")

        // when
        val previousChat: ChatListDto =
            chatFacadeService.getPreviousChat(
                userId,
                ChatQuery(currentChat.id, chatRoom.id, 10),
            )

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
        fixture.chatFixture.deleteAll()

        val currentChat: Chat = fixture.chatFixture.createPersistedChat(chatRoom.id, userId, "현재 테스트 메시지")

        // when
        val previousChat: ChatListDto =
            chatFacadeService.getPreviousChat(
                userId,
                ChatQuery(currentChat.id, chatRoom.id, 10),
            )

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
        val prevChats = fixture.chatFixture.createPersistedChats(chatRoom.id, userId, 10)
        val currentChat: Chat = fixture.chatFixture.createPersistedChat(chatRoom.id, userId, "현재 테스트 메시지")
        fixture.chatFixture.createPersistedChats(chatRoom.id, userId, 30)

        // when
        val findAfterChats: ChatListDto =
            chatFacadeService.getAfterChat(
                userId,
                ChatQuery(currentChat.id, chatRoom.id, 10),
            )

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
        val prevChats: List<Chat> = fixture.chatFixture.createPersistedChats(chatRoom.id, userId, 30)
        val currentChat: Chat = fixture.chatFixture.createPersistedChat(chatRoom.id, userId, "현재 테스트 메시지")
        fixture.chatFixture.createPersistedChats(chatRoom.id, userId, 10)

        // when
        val findPrevChats: ChatListDto =
            chatFacadeService.getAfterChat(
                userId,
                ChatQuery(currentChat.id, chatRoom.id, 10),
            )

        // then
        assertThat(findPrevChats.chatList).hasSize(10)
        assertThat(findPrevChats.cursor).isNotEqualTo(prevChats.last().id)
    }

    @Test
    @DisplayName("최근 채팅 조회 테스트")
    fun getRecentChatTest() {
        // given
        val userId = "test-user-id"
        val chatRoom = fixture.setUp(userId = userId)
        fixture.chatFixture.createPersistedChat(chatRoom.id, userId)

        // when
        val recentChat: ChatWithUser<*> =
            chatFacadeService.getRecentChat(userId, chatRoom.id)

        // then
        assertThat(recentChat.id).isEqualTo(recentChat.id)
        assertThat(recentChat.user.id).isEqualTo(userId)
    }

    @Test
    @DisplayName("채팅을 읽은 적이 없는 상태에서 최근 채팅 조회 테스트")
    fun getChatTestWhenChatNotExist() {
        // given
        val userId = "test-user-id"
        fixture.userFixture.createPersistedUser(userId)
        val otherUser = fixture.userFixture.createPersistedUser("other-user-id")

        val workspace = fixture.workspaceFixture.createPersistedWorkspace()
        fixture.workspaceUserFixture.createPersistedWorkspaceUser(workspace.id, userId, listOf(userId, otherUser.id))

        val chatRoom = fixture.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.DEFAULT)
        fixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, listOf(userId, otherUser.id))

        fixture.chatFixture.createPersistedChats(chatRoom.id, otherUser.id, 10)

        // when & then
        assertThatThrownBy { chatFacadeService.getRecentChat(userId, chatRoom.id) }
            .isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.CHAT_NOT_FOUND)
    }
}
