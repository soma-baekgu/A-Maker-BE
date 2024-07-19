package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.Chat
import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.dto.ChatRoomCreateDto
import com.backgu.amaker.chat.dto.ChatRoomsViewDto
import com.backgu.amaker.fixture.ChatRoomFacadeFixture
import com.backgu.amaker.workspace.domain.Workspace
import com.backgu.amaker.workspace.domain.WorkspaceUserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@DisplayName("ChatRoomFacadeService 테스트")
@Transactional
@SpringBootTest
class ChatRoomFacadeServiceTest {
    @Autowired
    lateinit var chatRoomFacadeService: ChatRoomFacadeService

    companion object {
        lateinit var fixtures: ChatRoomFacadeFixture
        private const val TEST_USER_ID: String = "test-user"
        lateinit var chatRoom: ChatRoom
        lateinit var workspace: Workspace

        @JvmStatic
        @BeforeAll
        fun setUp(
            @Autowired fixtures: ChatRoomFacadeFixture,
        ) {
            val chatRoomFixtureDto = fixtures.setUp(TEST_USER_ID)
            chatRoom = chatRoomFixtureDto.chatRoom
            workspace = chatRoomFixtureDto.workspace
            this.fixtures = fixtures
        }
    }

    @Test
    @DisplayName("채팅방 생성 테스트")
    fun createChatRoom() {
        // given
        val leaderId = "tester"
        val workspace = fixtures.setUp(userId = leaderId).workspace
        val activeWorkspaceMember = fixtures.userFixture.createPersistedUsers(10)
        fixtures.workspaceUserFixture.createPersistedWorkspaceMember(
            workspaceId = workspace.id,
            memberIds = activeWorkspaceMember.map { it.id },
        )
        val inactiveWorkspaceMember = fixtures.userFixture.createPersistedUsers(10)
        fixtures.workspaceUserFixture.createPersistedWorkspaceMember(
            workspaceId = workspace.id,
            memberIds = inactiveWorkspaceMember.map { it.id },
            WorkspaceUserStatus.PENDING,
        )

        // when
        val chatRoom = chatRoomFacadeService.createChatRoom(leaderId, workspace.id, ChatRoomCreateDto("test"))

        // then
        assertThat(chatRoom).isNotNull()
        assertThat(chatRoom.workspaceId).isEqualTo(workspace.id)
    }

    @Test
    @DisplayName("유저가 속한 채팅방 조회 성공")
    fun findChatRoomsJoined() {
        // given
        val workspaceId = 1L
        val lastChat: Chat = fixtures.chatFixture.createPersistedChat(1L, TEST_USER_ID, "content")
        fixtures.chatRoomFixture.save(chatRoom.updateLastChatId(lastChat))

        // when
        val result: ChatRoomsViewDto = chatRoomFacadeService.findChatRoomsJoined(TEST_USER_ID, workspaceId)

        // then
        assertThat(result.chatRooms).isNotNull
        assertThat(result.chatRooms.size).isOne()
        assertThat(result.chatRooms[0].unreadChatCount).isEqualTo(1)
        assertThat(result.chatRooms[0].lastChat?.content).isEqualTo("content")
        assertThat(result.chatRooms[0].participants.size).isEqualTo(11)
    }
}
