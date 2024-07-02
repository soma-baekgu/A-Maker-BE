package com.backgu.amaker.workspace.service

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.fixture.WorkspaceFixture.Companion.createWorkspaceRequest
import com.backgu.amaker.fixture.WorkspaceFixtureFacade
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("WorkspaceFacadeService 테스트")
@Transactional
@SpringBootTest
class WorkspaceFacadeServiceTest {
    @Autowired
    lateinit var workspaceFacadeService: WorkspaceFacadeService

    @Autowired
    lateinit var fixtures: WorkspaceFixtureFacade

    @Test
    @DisplayName("워크 스페이스 생성 테스트")
    fun createWorkspace() {
        // given
        val userId = "tester"
        fixtures.user.createPersistedUser(userId)

        val request = createWorkspaceRequest("워크스페이스 생성")

        // when
        val result = workspaceFacadeService.createWorkspace(userId, request)

        // then
        assertThat(result.name).isEqualTo("워크스페이스 생성")
    }

    @Test
    @DisplayName("유저의 워크스페이스들 조회")
    fun findWorkspaces() {
        // given
        val userId = "tester"
        fixtures.user.createPersistedUser(userId)
        val workspace1 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스1")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace1.id, leaderId = userId)
        val workspace2 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스2")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace2.id, leaderId = userId)
        val workspace3 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스3")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace3.id, leaderId = userId)
        val workspace4 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스4")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace4.id, leaderId = userId)

        // when
        val result = workspaceFacadeService.findWorkspaces(userId)

        // then
        assertThat(result.userId).isEqualTo(userId)
        assertThat(result.workspaces.size).isEqualTo(4)
    }

    @Test
    @DisplayName("유저의 기본 워크스페이스 조회")
    fun findDefaultWorkspace() {
        // given
        val userId = "tester"
        fixtures.user.createPersistedUser(userId)
        val workspace1 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스1")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace1.id, leaderId = userId)

        val workspace2 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스2")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace2.id, leaderId = userId)

        // when
        val result = workspaceFacadeService.getDefaultWorkspace(userId)

        // then
        assertThat(result.workspaceId).isEqualTo(workspace2.id)
        assertThat(result.name).isEqualTo("워크스페이스2")
    }

    @Test
    @DisplayName("기본 워크스페이스를 찾을 수 없을 때 실패")
    fun failFindDefaultWorkspace() {
        // given
        val userId = "tester"
        fixtures.user.createPersistedUser(userId)

        // when & then
        assertThatThrownBy { workspaceFacadeService.getDefaultWorkspace(userId) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage("Default workspace not found : $userId")
    }

    @Test
    @DisplayName("워크스페이스의 그룹 채팅방을 조회")
    fun getGroupChatRoom() {
        // given
        val userId = "tester"
        fixtures.user.createPersistedUser(userId)
        val workspace = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스1")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace.id, leaderId = userId)
        val chatRoom =
            fixtures.chatRoom.createPersistedChatRoom(workspaceId = workspace.id, chatRoomType = ChatRoomType.GROUP)
        fixtures.chatRoomUser.createPersistedChatRoomUser(chatRoomId = chatRoom.id, userIds = listOf(userId))

        // when
        val result = workspaceFacadeService.getGroupChatRoom(workspace.id, userId)

        // then
        assertThat(result.chatRoomId).isEqualTo(chatRoom.id)
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp(
            @Autowired workspaceFixtureFacade: WorkspaceFixtureFacade,
        ) {
            workspaceFixtureFacade.setUp()
        }
    }
}
