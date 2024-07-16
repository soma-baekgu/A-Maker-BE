package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.fixture.ChatFixtureFacade
import org.assertj.core.api.Assertions.assertThat
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
        val user = chatFacadeFixture.user.createPersistedUser("findPreviousChatList")
        val workspace = chatFacadeFixture.workspace.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoom.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.user.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chat.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        val cursorChat = chatFacadeFixture.chat.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chat.createPersistedRandomUserChats(
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
        val user = chatFacadeFixture.user.createPersistedUser()
        val workspace = chatFacadeFixture.workspace.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoom.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.user.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chat.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        chatFacadeFixture.setUp("different-set-up")

        val cursorChat = chatFacadeFixture.chat.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chat.createPersistedRandomUserChats(
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
        val user = chatFacadeFixture.user.createPersistedUser()
        val workspace = chatFacadeFixture.workspace.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoom.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.user.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chat.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        val cursorChat = chatFacadeFixture.chat.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chat.createPersistedRandomUserChats(
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
        val user = chatFacadeFixture.user.createPersistedUser()
        val workspace = chatFacadeFixture.workspace.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoom.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.user.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        val cursorChat = chatFacadeFixture.chat.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chat.createPersistedRandomUserChats(
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
        val user = chatFacadeFixture.user.createPersistedUser()
        val workspace = chatFacadeFixture.workspace.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoom.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.user.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chat.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        val cursorChat = chatFacadeFixture.chat.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chat.createPersistedRandomUserChats(
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
        val user = chatFacadeFixture.user.createPersistedUser()
        val workspace = chatFacadeFixture.workspace.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoom.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.user.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chat.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        val cursorChat = chatFacadeFixture.chat.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chat.createPersistedRandomUserChats(
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
        val user = chatFacadeFixture.user.createPersistedUser()
        val workspace = chatFacadeFixture.workspace.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoom.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.user.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        chatFacadeFixture.chat.createPersistedRandomUserChats(
            chatRoom.id,
            chatRoomUsers.map { it.id }.plus(user.id),
            10,
        )

        val cursorChat = chatFacadeFixture.chat.createPersistedChat(chatRoom.id, user.id)

        chatFacadeFixture.chat.createPersistedRandomUserChats(
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
        val user = chatFacadeFixture.user.createPersistedUser()
        val workspace = chatFacadeFixture.workspace.createPersistedWorkspace()
        val chatRoom = chatFacadeFixture.chatRoom.createPersistedChatRoom(workspace.id, ChatRoomType.GROUP)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, arrayListOf(user.id))
        val chatRoomUsers = chatFacadeFixture.user.createPersistedUsers(10)
        chatFacadeFixture.chatRoomUser.createPersistedChatRoomUser(chatRoom.id, chatRoomUsers.map { it.id })

        val cursorChat = chatFacadeFixture.chat.createPersistedChat(chatRoom.id, user.id)

        // when
        val chatList = chatService.findAfterChatList(chatRoom.id, cursorChat.id, 10)

        // then
        assertThat(chatList.size).isEqualTo(0)
    }
}
