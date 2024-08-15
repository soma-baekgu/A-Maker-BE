package com.backgu.amaker.api.chat.service

import com.backgu.amaker.api.chat.dto.BriefChatRoomViewDto
import com.backgu.amaker.api.chat.dto.ChatRoomsViewDto
import com.backgu.amaker.api.common.container.IntegrationTest
import com.backgu.amaker.api.fixture.ChatRoomFacadeFixture
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatRoomType
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.domain.workspace.WorkspaceUserStatus
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@DisplayName("ChatRoomFacadeService 테스트")
@Transactional
class ChatRoomFacadeServiceTest : IntegrationTest() {
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
        val chatRoom =
            chatRoomFacadeService.createChatRoom(
                leaderId,
                workspace.id,
                com.backgu.amaker.api.chat.dto
                    .ChatRoomCreateDto("test"),
            )

        // then
        assertThat(chatRoom).isNotNull()
        assertThat(chatRoom.workspaceId).isEqualTo(workspace.id)
    }

    @Test
    @DisplayName("유저가 속한 채팅방 조회 성공")
    fun findChatRoomsJoined() {
        // given
        val lastChat: Chat = fixtures.chatFixture.createPersistedChat(chatRoom.id, TEST_USER_ID, "content")
        fixtures.chatRoomFixture.save(chatRoom.updateLastChatId(lastChat))

        // when
        val result: ChatRoomsViewDto = chatRoomFacadeService.findChatRoomsJoined(TEST_USER_ID, workspace.id)

        // then
        assertThat(result.chatRooms).isNotNull
        assertThat(result.chatRooms.size).isOne()
        assertThat(result.chatRooms[0].unreadChatCount).isEqualTo(1)
        assertThat(result.chatRooms[0].lastChat?.content).isEqualTo("content")
        assertThat(result.chatRooms[0].participants.size).isEqualTo(11)
    }

    @Test
    @DisplayName("워크스페이스에 속한 채팅방 조회 성공")
    fun findChatRooms() {
        // given
        fixtures.setUp(userId = "other1")
        fixtures.setUp(userId = "other2")
        fixtures.setUp(userId = "other3")

        val leaderId = "leader"
        val leader = fixtures.userFixture.createPersistedUser(leaderId)
        val member = fixtures.userFixture.createPersistedUsers(10)
        val workspace = fixtures.workspaceFixture.createPersistedWorkspace(name = "test-workspace")
        fixtures.workspaceUserFixture.createPersistedWorkspaceUser(workspace.id, leader.id, member.map { it.id })

        val defaultChatRoom = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.DEFAULT)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(
            defaultChatRoom.id,
            member.map { it.id }.plus(leader.id),
        )

        val leaderNotRegistered1 = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(leaderNotRegistered1.id, member.map { it.id })
        val leaderNotRegistered2 = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(leaderNotRegistered2.id, member.map { it.id })
        val leaderNotRegistered3 = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(leaderNotRegistered3.id, member.map { it.id })

        val leadRegistered1 = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(
            leadRegistered1.id,
            member.map { it.id }.plus(leader.id),
        )
        val leadRegistered2 = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(
            leadRegistered2.id,
            member.map { it.id }.plus(leader.id),
        )

        // when
        val result: BriefChatRoomViewDto = chatRoomFacadeService.findChatRooms(leaderId, workspace.id)

        // then
        assertThat(result.chatRooms).isNotNull
        assertThat(result.chatRooms.size).isEqualTo(6)

        assertThat(result.chatRooms[0].participants.size).isEqualTo(11)
    }

    @Test
    @DisplayName("워크스페이스에 속하지 않은 채팅방 조회 성공")
    fun findChatRoomsNotRegistered() {
        // given
        fixtures.setUp(userId = "other1")
        fixtures.setUp(userId = "other2")
        fixtures.setUp(userId = "other3")

        val leaderId = "leader"
        val leader = fixtures.userFixture.createPersistedUser(leaderId)
        val member = fixtures.userFixture.createPersistedUsers(10)
        val workspace = fixtures.workspaceFixture.createPersistedWorkspace(name = "test-workspace")
        fixtures.workspaceUserFixture.createPersistedWorkspaceUser(workspace.id, leader.id, member.map { it.id })

        val defaultChatRoom = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.DEFAULT)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(
            defaultChatRoom.id,
            member.map { it.id }.plus(leader.id),
        )

        val leaderNotRegistered1 = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(leaderNotRegistered1.id, member.map { it.id })
        val leaderNotRegistered2 = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(leaderNotRegistered2.id, member.map { it.id })
        val leaderNotRegistered3 = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(leaderNotRegistered3.id, member.map { it.id })

        val leadRegistered1 = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(
            leadRegistered1.id,
            member.map { it.id }.plus(leader.id),
        )
        val leadRegistered2 = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(
            leadRegistered2.id,
            member.map { it.id }.plus(leader.id),
        )

        // when
        val result: BriefChatRoomViewDto = chatRoomFacadeService.findNotRegisteredChatRooms(leaderId, workspace.id)

        // then
        assertThat(result.chatRooms).isNotNull
        assertThat(result.chatRooms.size).isEqualTo(3)
        assertThat(leaderNotRegistered1.id).isIn(
            result.chatRooms.map { it.chatRoomId },
        )
        assertThat(leaderNotRegistered2.id).isIn(
            result.chatRooms.map { it.chatRoomId },
        )
        assertThat(leaderNotRegistered3.id).isIn(
            result.chatRooms.map { it.chatRoomId },
        )
    }

    @Test
    @DisplayName("워크스페이스에 채팅방이 없을 때 채팅방 조회 성공")
    fun findChatRoomsNoChatRoom() {
        // given
        fixtures.setUp(userId = "other1")
        fixtures.setUp(userId = "other2")
        fixtures.setUp(userId = "other3")

        val leaderId = "leader"
        val leader = fixtures.userFixture.createPersistedUser(leaderId)
        val member = fixtures.userFixture.createPersistedUsers(10)
        val workspace = fixtures.workspaceFixture.createPersistedWorkspace(name = "test-workspace")
        fixtures.workspaceUserFixture.createPersistedWorkspaceUser(workspace.id, leader.id, member.map { it.id })

        // when
        val result: BriefChatRoomViewDto = chatRoomFacadeService.findChatRooms(leaderId, workspace.id)

        // then
        assertThat(result.chatRooms).isNotNull
        assertThat(result.chatRooms.size).isEqualTo(0)
    }

    @Test
    @DisplayName("채팅방 가입 성공")
    fun joinChatRoom() {
        // given
        val (workspace, defaultChatRoom, members) = fixtures.setUp(userId = "leader")
        val newChatRoom = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)

        // when
        val chatRoomUser = chatRoomFacadeService.joinChatRoom(members[0].id, workspace.id, newChatRoom.id)

        // then
        assertThat(chatRoomUser).isNotNull
        assertThat(chatRoomUser.chatRoomId).isEqualTo(newChatRoom.id)
        assertThat(chatRoomUser.userId).isEqualTo(members[0].id)
        assertThat(chatRoomUser.lastReadChatId).isNull()
    }

    @Test
    @DisplayName("없는 채팅방 가입 요청")
    fun joinNoChatRoom() {
        // given
        val leaderId = "leader"
        val leader = fixtures.userFixture.createPersistedUser(leaderId)
        val members = fixtures.userFixture.createPersistedUsers(10)
        val workspace = fixtures.workspaceFixture.createPersistedWorkspace(name = "test-workspace")
        fixtures.workspaceUserFixture.createPersistedWorkspaceUser(workspace.id, leader.id, members.map { it.id })

        val noChatRoomId = 1L
        fixtures.chatRoomFixture.deleteChatRoom(noChatRoomId)

        // when & then
        assertThatThrownBy {
            chatRoomFacadeService.joinChatRoom(
                members[0].id,
                workspace.id,
                noChatRoomId,
            )
        }.isInstanceOf(BusinessException::class.java).extracting("statusCode").isEqualTo(StatusCode.CHAT_ROOM_NOT_FOUND)
    }

    @Test
    @DisplayName("이미 가입된 채팅방에 대한 요청")
    fun joinAlreadyJoinedChatRoom() {
        // given
        val (workspace, defaultChatRoom, members) = fixtures.setUp(userId = "leader")
        val newChatRoom = fixtures.chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.CUSTOM)
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(newChatRoom.id, members.map { it.id })

        // when & then
        assertThatThrownBy {
            chatRoomFacadeService.joinChatRoom(
                members[0].id,
                workspace.id,
                newChatRoom.id,
            )
        }.isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.CHAT_ROOM_USER_ALREADY_EXIST)
    }

    @Test
    @DisplayName("채팅방 조회")
    fun getChatRoom() {
        // given
        val (workspace, defaultChatRoom, members) = fixtures.setUp(userId = "leader")

        // when
        val result = chatRoomFacadeService.getChatRoom(members[0].id, defaultChatRoom.id)

        // then
        assertThat(result).isNotNull
        assertThat(result.chatRoomId).isEqualTo(defaultChatRoom.id)
    }

    @Test
    @DisplayName("채팅방 조회 실패 - 권한 없는 채팅방 조회")
    fun getChatRoomNotIn() {
        // given
        val userId = "tester"
        val (workspace, defaultChatRoom, members) = fixtures.setUp(userId = "leader")
        fixtures.userFixture.createPersistedUser(userId)

        // when & then
        assertThatThrownBy { chatRoomFacadeService.getChatRoom(userId, defaultChatRoom.id) }
            .isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.CHAT_ROOM_USER_NOT_FOUND)
    }

    @Test
    @DisplayName("채팅방 조회 실패 - 채팅방이 없는 경우")
    fun getChatRoomNotFound() {
        // given
        val userId = "tester"
        val chatRoomId = 0L
        fixtures.userFixture.createPersistedUser(userId)

        // when & then
        assertThatThrownBy { chatRoomFacadeService.getChatRoom(userId, chatRoomId) }
            .isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.CHAT_ROOM_NOT_FOUND)
    }

    @Test
    @DisplayName("채팅방 사용자 조회")
    fun getChatRoomUsers() {
        // given

        // when
        val result = chatRoomFacadeService.getChatRoomUsers(TEST_USER_ID, chatRoom.id)

        // then
        assertThat(result).isNotNull
        assertThat(result.users.size).isEqualTo(11)
    }

    @Test
    @DisplayName("채팅방 사용자 조회 실패 - 권한 없는 채팅방 사용자 조회")
    fun failGetChatRoomUsersNotIn() {
        // given
        val userId = "tester"
        fixtures.userFixture.createPersistedUser(userId)

        // when & then
        assertThatThrownBy {
            chatRoomFacadeService.getChatRoomUsers(userId, chatRoom.id)
        }.isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.CHAT_ROOM_USER_NOT_FOUND)
    }
}
