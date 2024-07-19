package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.fixture.ChatRoomFacadeFixture
import com.backgu.amaker.workspace.domain.WorkspaceUserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@DisplayName("ChatRoomService 테스트")
class ChatRoomServiceTest {
    @Autowired
    lateinit var chatRoomService: ChatRoomService

    @Autowired
    lateinit var fixture: ChatRoomFacadeFixture

    @Test
    @DisplayName("일반 채팅 생성을 위한 테스트")
    fun chatRoomSaveWithNewChat() {
        // given
        val tester = fixture.userFixture.createPersistedUser()
        val workspace = fixture.workspaceFixture.createPersistedWorkspace()
        val chatRoom = fixture.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.DEFAULT)
        fixture.chatRoomUserFixture.createPersistedChatRoomUser(chatRoom.id, arrayListOf(tester.id))
        val chat = fixture.chatFixture.createPersistedChat(chatRoom.id, tester.id)
        val savedChatRoom = chatRoomService.save(chatRoom.updateLastChatId(chat))

        // when
        val findChatRoom = chatRoomService.getById(savedChatRoom.id)

        // then
        assertThat(findChatRoom.id).isEqualTo(savedChatRoom.id)
        assertThat(findChatRoom.lastChatId).isEqualTo(chat.id)
    }

    @Test
    @DisplayName("채팅방 생성을 위한 save 테스트")
    fun saveChatRoom() {
        // given
        val leaderId = "tester"
        val workspace = fixture.setUp(userId = leaderId).workspace
        val activeWorkspaceMember = fixture.userFixture.createPersistedUsers(10)
        fixture.workspaceUserFixture.createPersistedWorkspaceMember(
            workspaceId = workspace.id,
            memberIds = activeWorkspaceMember.map { it.id },
        )
        val inactiveWorkspaceMember = fixture.userFixture.createPersistedUsers(10)
        fixture.workspaceUserFixture.createPersistedWorkspaceMember(
            workspaceId = workspace.id,
            memberIds = inactiveWorkspaceMember.map { it.id },
            WorkspaceUserStatus.PENDING,
        )
        val savedChatRoom = chatRoomService.save(workspace.createCustomChatRoom("test"))

        // when
        val findChatRoom = chatRoomService.getById(savedChatRoom.id)

        // then
        assertThat(findChatRoom.id).isEqualTo(savedChatRoom.id)
        assertThat(findChatRoom.name).isEqualTo("test")
    }
}
