package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.common.exception.StatusCode
import com.backgu.amaker.fixture.ChatFixtureFacade
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@DisplayName("ChatService 테스트")
@Transactional
@SpringBootTest
class ChatServiceTest {
    @Autowired
    lateinit var chatService: ChatService

    @Autowired
    lateinit var chatFacadeFixture: ChatFixtureFacade

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp(
            @Autowired fixture: ChatFixtureFacade,
        ) {
            fixture.setUp(userId = "basic-data-set-up")
        }
    }

    @Test
    @DisplayName("커서 이전 채팅 조회 테스트")
    fun findPreviousChatList() {
        // given
        val user = chatFacadeFixture.userFixture.createPersistedUser("findPreviousChatList")
        val workspace = chatFacadeFixture.workspaceFixture.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.userFixture.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        val cursorChat = chatFacadeFixture.chatFixture.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            12,
        )

        chatFacadeFixture.setUp("different-set-up")

        // when
        val chatList = chatService.findPreviousChatList(chatRoom.id, cursorChat.id, 5)

        // then
        assertThat(chatList.size).isEqualTo(5)
        assertThat(chatList.first().id).isLessThan(cursorChat.id)
        assertThat(chatList.last().id).isLessThan(cursorChat.id)
        assertThat(chatList).isSortedAccordingTo(Comparator.comparingLong { it.id })
    }

    @Test
    @DisplayName("커서 이전 채팅 조회 테스트: 개수가 부족할 때")
    fun findPreviousChatListWithLessCount() {
        // given
        val user = chatFacadeFixture.userFixture.createPersistedUser()
        val workspace = chatFacadeFixture.workspaceFixture.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.userFixture.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        chatFacadeFixture.setUp("different-set-up")

        val cursorChat = chatFacadeFixture.chatFixture.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            12,
        )

        // when
        val chatList = chatService.findPreviousChatList(chatRoom.id, cursorChat.id, 15)

        // then
        assertThat(chatList.size).isEqualTo(10)
        assertThat(chatList.first().id).isLessThan(cursorChat.id)
        assertThat(chatList.last().id).isLessThan(cursorChat.id)
        assertThat(chatList).isSortedAccordingTo(Comparator.comparingLong { it.id })
    }

    @Test
    @DisplayName("커서 이전 채팅 조회 테스트: 개수가 맞아 떨어질 때")
    fun findPreviousChatWithExactlyChatCount() {
        // given
        val user = chatFacadeFixture.userFixture.createPersistedUser()
        val workspace = chatFacadeFixture.workspaceFixture.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.userFixture.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        val cursorChat = chatFacadeFixture.chatFixture.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            12,
        )

        chatFacadeFixture.setUp("different-set-up")

        // when
        val chatList = chatService.findPreviousChatList(chatRoom.id, cursorChat.id, 10)

        // then
        assertThat(chatList.size).isEqualTo(10)
        assertThat(chatList.first().id).isLessThan(cursorChat.id)
        assertThat(chatList.last().id).isLessThan(cursorChat.id)
        assertThat(chatList).isSortedAccordingTo(Comparator.comparingLong { it.id })
    }

    @Test
    @DisplayName("커서 이전 채팅 조회 테스트: 개수가 0일 때")
    fun findPreviousChatWithZeroCount() {
        // given
        val user = chatFacadeFixture.userFixture.createPersistedUser()
        val workspace = chatFacadeFixture.workspaceFixture.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.userFixture.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        val cursorChat = chatFacadeFixture.chatFixture.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            12,
        )

        // when
        val chatList = chatService.findPreviousChatList(chatRoom.id, cursorChat.id, 10)

        // then
        assertThat(chatList.size).isEqualTo(0)
    }

    @Test
    @DisplayName("커서 이후 채팅 조회 테스트")
    fun findNextChatList() {
        // given
        val user = chatFacadeFixture.userFixture.createPersistedUser()
        val workspace = chatFacadeFixture.workspaceFixture.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.userFixture.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        val cursorChat = chatFacadeFixture.chatFixture.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        chatFacadeFixture.setUp("different-set-up")

        // when
        val chatList = chatService.findAfterChatList(chatRoom.id, cursorChat.id, 5)

        // then
        assertThat(chatList.size).isEqualTo(5)
        assertThat(chatList.first().id).isGreaterThan(cursorChat.id)
        assertThat(chatList.last().id).isGreaterThan(cursorChat.id)
        assertThat(chatList).isSortedAccordingTo(Comparator.comparingLong { it.id })
    }

    @Test
    @DisplayName("커서 이후 채팅 조회 테스트: 개수가 부족할 때")
    fun findAfterChatListWithLessCount() {
        // given
        val user = chatFacadeFixture.userFixture.createPersistedUser()
        val workspace = chatFacadeFixture.workspaceFixture.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.userFixture.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        val cursorChat = chatFacadeFixture.chatFixture.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        // when
        val chatList = chatService.findAfterChatList(chatRoom.id, cursorChat.id, 15)

        // then
        assertThat(chatList.size).isEqualTo(10)
        assertThat(chatList.first().id).isGreaterThan(cursorChat.id)
        assertThat(chatList.last().id).isGreaterThan(cursorChat.id)
        assertThat(chatList).isSortedAccordingTo(Comparator.comparingLong { it.id })
    }

    @Test
    @DisplayName("커서 이후 채팅 조회 테스트: 개수가 맞아 떨어질 때")
    fun findAfterChatListWithExactlyCount() {
        // given
        val user = chatFacadeFixture.userFixture.createPersistedUser()
        val workspace = chatFacadeFixture.workspaceFixture.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.userFixture.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        val cursorChat = chatFacadeFixture.chatFixture.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chatFixture.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        // when
        val chatList = chatService.findAfterChatList(chatRoom.id, cursorChat.id, 10)

        // then
        assertThat(chatList.size).isEqualTo(10)
        assertThat(chatList.first().id).isGreaterThan(cursorChat.id)
        assertThat(chatList.last().id).isGreaterThan(cursorChat.id)
        assertThat(chatList).isSortedAccordingTo(Comparator.comparingLong { it.id })
    }

    @Test
    @DisplayName("커서 이후 채팅 조회 테스트: 개수가 0일 때")
    fun findAfterChatListWithZeroCount() {
        // given
        val user = chatFacadeFixture.userFixture.createPersistedUser()
        val workspace = chatFacadeFixture.workspaceFixture.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.userFixture.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        val cursorChat = chatFacadeFixture.chatFixture.createPersistedChat(chatRoom.id, user.id)

        // when
        val chatList = chatService.findAfterChatList(chatRoom.id, cursorChat.id, 10)

        // then
        assertThat(chatList.size).isEqualTo(0)
    }

    @Test
    @DisplayName("채팅 조회 테스트")
    fun findOneTest() {
        // given
        val user = chatFacadeFixture.userFixture.createPersistedUser()
        val workspace = chatFacadeFixture.workspaceFixture.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.userFixture.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        val targetChat = chatFacadeFixture.chatFixture.createPersistedChat(chatRoom.id, user.id)

        // when
        val findChat = chatService.findOneWithUser(targetChat.id)

        // then
        assertThat(findChat.id).isEqualTo(targetChat.id)
        assertThat(findChat.chatRoomId).isEqualTo(chatRoom.id)
        assertThat(findChat.content).isEqualTo(targetChat.content)
        assertThat(findChat.chatType).isEqualTo(targetChat.chatType)
        assertThat(findChat.createdAt).isEqualTo(targetChat.createdAt)
        assertThat(findChat.updatedAt).isEqualTo(targetChat.updatedAt)
        assertThat(findChat.user.id).isEqualTo(user.id)
        assertThat(findChat.user.name).isEqualTo(user.name)
        assertThat(findChat.user.email).isEqualTo(user.email)
        assertThat(findChat.user.picture).isEqualTo(user.picture)
    }

    @Test
    @DisplayName("채팅 조회 테스트: 채팅 아이디가 없을 때")
    fun findOneWithUserWithNoChatId() {
        // given & when & then
        assertThatThrownBy { chatService.findOneWithUser(null) }
            .isInstanceOf(RuntimeException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.CHAT_NOT_FOUND)
    }

    @Test
    @DisplayName("채팅 조회 테스트: 채팅이 없을 때")
    fun findOneWithUserWhenChatNotFound() {
        // given
        chatFacadeFixture.deleteAll()

        // when & then
        assertThatThrownBy { chatService.findOneWithUser(1L) }
            .isInstanceOf(RuntimeException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.CHAT_NOT_FOUND)
    }
}
