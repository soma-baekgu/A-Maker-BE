package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.dto.ChatRoomCreateDto
import com.backgu.amaker.fixture.ChatRoomFacadeFixture
import com.backgu.amaker.workspace.domain.WorkspaceUserStatus
import org.assertj.core.api.Assertions.assertThat
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

    @Autowired
    lateinit var fixtures: ChatRoomFacadeFixture

    @Test
    @DisplayName("채팅방 생성 테스트")
    fun createChatRoom() {
        // given
        val leaderId = "tester"
        val workspace = fixtures.setUp(userId = leaderId)
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
}
