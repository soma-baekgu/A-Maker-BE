package com.backgu.amaker.api.workspace.service

import com.backgu.amaker.api.common.container.IntegrationTest
import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.api.common.exception.StatusCode
import com.backgu.amaker.api.fixture.WorkspaceFixture.Companion.createWorkspaceRequest
import com.backgu.amaker.api.fixture.WorkspaceFixtureFacade
import com.backgu.amaker.application.notification.service.NotificationEventService
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatRoomType
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.WorkspaceRole
import com.backgu.amaker.domain.workspace.WorkspaceUser
import com.backgu.amaker.domain.workspace.WorkspaceUserStatus
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.transaction.annotation.Transactional

@DisplayName("WorkspaceFacadeService 테스트")
@Transactional
@AutoConfigureMockMvc
class WorkspaceFacadeServiceTest : IntegrationTest() {
    @Autowired
    private lateinit var workspaceFixtureFacade: WorkspaceFixtureFacade

    @Autowired
    lateinit var workspaceFacadeService: WorkspaceFacadeService

    @Autowired
    lateinit var fixtures: WorkspaceFixtureFacade

    @Autowired
    lateinit var workspaceUserService: WorkspaceUserService

    @MockkBean
    lateinit var notificationEventService: NotificationEventService

    @BeforeEach
    fun setUp() {
        workspaceFixtureFacade.setUp()
    }

    @Test
    @DisplayName("워크 스페이스 생성 테스트")
    fun createWorkspace() {
        // given
        every { notificationEventService.publishNotificationEvent(any()) } returns Unit

        val userId = "tester"
        fixtures.user.createPersistedUser(userId)

        val request = createWorkspaceRequest("워크스페이스 생성")

        // when
        val result = workspaceFacadeService.createWorkspace(userId, request)

        // then
        assertThat(result.name).isEqualTo("워크스페이스 생성")
        verify(exactly = 1) { notificationEventService.publishNotificationEvent(any()) }
    }

    @Test
    @DisplayName("초대자들이 있는 워크 스페이스 생성 테스트")
    fun createWorkspaceWithInvitee() {
        // given
        every { notificationEventService.publishNotificationEvent(any()) } returns Unit

        val userId = "tester"
        fixtures.user.createPersistedUser(userId)
        fixtures.user.createPersistedUser(email = "a@example.com")
        fixtures.user.createPersistedUser(email = "b@example.com")

        val request = createWorkspaceRequest("워크스페이스 생성", setOf("a@example.com", "b@example.com"))

        // when
        val result = workspaceFacadeService.createWorkspace(userId, request)

        // then
        assertThat(result.name).isEqualTo("워크스페이스 생성")
        verify(exactly = 3) { notificationEventService.publishNotificationEvent(any()) }
    }

    @Test
    @DisplayName("워크스페이스 리더가 초대자로 들어가 있는 테스트")
    fun createWorkspaceWithDuplicatedInvitees() {
        // given
        every { notificationEventService.publishNotificationEvent(any()) } returns Unit

        val userId = "tester"
        val userEmail = "tester@gmail.com"
        fixtures.user.createPersistedUser(
            id = userId,
            name = "tester",
            email = userEmail,
            picture = "http://server/picture-tester",
        )

        val inviteeEmail = "abc@gmail.com"
        fixtures.user.createPersistedUser(name = "abc", email = inviteeEmail, picture = "http://server/picture-abc")

        val request = createWorkspaceRequest("워크스페이스 생성", setOf(userEmail, inviteeEmail))

        // when & then
        assertThatThrownBy { workspaceFacadeService.createWorkspace(userId, request) }
            .isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.INVALID_WORKSPACE_CREATE)
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
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("워크스페이스를 찾을 수 없습니다.")
            .extracting("statusCode")
            .isEqualTo(StatusCode.WORKSPACE_NOT_FOUND)
    }

    @Test
    @DisplayName("워크스페이스 유저 활성화")
    fun activateWorkspaceUser() {
        // given
        every { notificationEventService.publishNotificationEvent(any()) } returns Unit

        val leaderId = "leader"
        fixtures.user.createPersistedUser(leaderId)
        val workspace = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스")

        val memberId = "member"
        val member: User = fixtures.user.createPersistedUser(memberId)

        fixtures.workspaceUser.createPersistedWorkspaceLeader(
            workspaceId = workspace.id,
            leaderId = leaderId,
        )
        fixtures.workspaceUser.createPersistedWorkspaceMember(
            workspaceId = workspace.id,
            memberIds = listOf(memberId),
            WorkspaceUserStatus.PENDING,
        )

        val chatRoom: ChatRoom =
            fixtures.chatRoom.createPersistedChatRoom(workspaceId = workspace.id, chatRoomType = ChatRoomType.DEFAULT)
        fixtures.chatRoomUser.createPersistedChatRoomUser(chatRoomId = chatRoom.id, userIds = listOf(leaderId))

        // when
        workspaceFacadeService.activateWorkspaceUser(memberId, workspace.id)
        val workspaceUser: WorkspaceUser = workspaceUserService.getWorkspaceUser(workspace, member)

        // then
        assertThat(workspaceUser).isNotNull
        assertThat(workspaceUser.status).isEqualTo(WorkspaceUserStatus.ACTIVE)
        assertThat(workspaceUser.userId).isEqualTo(memberId)
        assertThat(workspaceUser.workspaceId).isEqualTo(workspace.id)
        assertThat(workspaceUser.workspaceRole).isEqualTo(WorkspaceRole.MEMBER)

        verify(exactly = 1) { notificationEventService.publishNotificationEvent(any()) }
    }

    @Test
    @DisplayName("워크스페이스 유저 활성화 실패")
    fun activateWorkspaceUserLimitedUser() {
        // given
        every { notificationEventService.publishNotificationEvent(any()) } returns Unit

        val leaderId = "leader"
        fixtures.user.createPersistedUser(leaderId)
        val workspace = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스")
        workspace.belongingNumber = workspace.workspacePlan.belongingLimit
        fixtures.workspace.save(workspace)

        val member: User = fixtures.user.createPersistedUser()

        fixtures.workspaceUser.createPersistedWorkspaceLeader(
            workspaceId = workspace.id,
            leaderId = leaderId,
        )
        fixtures.workspaceUser.createPersistedWorkspaceMember(
            workspaceId = workspace.id,
            memberIds = listOf(member.id),
            WorkspaceUserStatus.PENDING,
        )

        val chatRoom: ChatRoom =
            fixtures.chatRoom.createPersistedChatRoom(workspaceId = workspace.id, chatRoomType = ChatRoomType.DEFAULT)
        fixtures.chatRoomUser.createPersistedChatRoomUser(chatRoomId = chatRoom.id, userIds = listOf(leaderId))

        // when
        assertThatThrownBy { workspaceFacadeService.activateWorkspaceUser(member.id, workspace.id) }
            .isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.INVALID_WORKSPACE_JOIN)
    }

    @DisplayName("워크스페이스의 기본 채팅방을 조회")
    fun getDefaultChatRoom() {
        // given
        val userId = "tester"
        fixtures.user.createPersistedUser(userId)
        val workspace = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스1")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace.id, leaderId = userId)
        val chatRoom =
            fixtures.chatRoom.createPersistedChatRoom(workspaceId = workspace.id, chatRoomType = ChatRoomType.DEFAULT)
        fixtures.chatRoomUser.createPersistedChatRoomUser(chatRoomId = chatRoom.id, userIds = listOf(userId))

        // when
        val result = workspaceFacadeService.getDefaultChatRoom(workspace.id, userId)

        // then
        assertThat(result.chatRoomId).isEqualTo(chatRoom.id)
    }

    @Test
    @DisplayName("존재하지 않는 워크스페이스에 대한 유저 활성화 실패")
    fun activateFailWorkspaceUser() {
        // given
        workspaceFixtureFacade.deleteAll()
        val leaderId = "leader"
        fixtures.user.createPersistedUser(leaderId)

        val memberId = "member"
        fixtures.user.createPersistedUser(memberId)

        fixtures.workspaceUser.createPersistedWorkspaceUser(
            workspaceId = 0L,
            leaderId = leaderId,
            memberIds = listOf(memberId),
        )

        // when & then
        assertThatThrownBy { workspaceFacadeService.activateWorkspaceUser(memberId, 0L) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("워크스페이스를 찾을 수 없습니다.")
            .extracting("statusCode")
            .isEqualTo(StatusCode.WORKSPACE_NOT_FOUND)
    }
}
