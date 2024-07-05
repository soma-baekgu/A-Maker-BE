package com.backgu.amaker.workspace.service

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.exception.StatusCode
import com.backgu.amaker.fixture.WorkspaceFixtureFacade
import com.backgu.amaker.user.domain.User
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("WorkspaceUserService 테스트")
@Transactional
@SpringBootTest
class WorkspaceUserServiceTest {
    @Autowired
    lateinit var workspaceUserService: WorkspaceUserService

    @Autowired
    lateinit var fixtures: WorkspaceFixtureFacade

    @Test
    @DisplayName("유저가 속한 워크스페이스의 그룹 채팅방 조회 성공")
    fun getGroupChatRoomIn() {
        // given
        val userId = "tester"
        val user = fixtures.user.createPersistedUser(userId)
        val workspace = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스1")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace.id, leaderId = userId)
        val chatRoom =
            fixtures.chatRoom.createPersistedChatRoom(workspaceId = workspace.id, chatRoomType = ChatRoomType.GROUP)
        fixtures.chatRoomUser.createPersistedChatRoomUser(chatRoomId = chatRoom.id, userIds = listOf(userId))

        // when & then
        assertThatCode {
            workspaceUserService.validUserInWorkspace(user, workspace)
        }.doesNotThrowAnyException()
    }

    @Test
    @DisplayName("유저가 속하지 않는 워크스페이스의 그룹 채팅방 조회 실패")
    fun failGetGroupChatRoomNotIn() {
        // given
        val userId = "tester"
        val user: User = fixtures.user.createPersistedUser(userId)

        val diffUser = "diff tester"
        fixtures.user.createPersistedUser(diffUser)
        val workspace = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스1")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace.id, leaderId = diffUser)
        val chatRoom =
            fixtures.chatRoom.createPersistedChatRoom(workspaceId = workspace.id, chatRoomType = ChatRoomType.GROUP)
        fixtures.chatRoomUser.createPersistedChatRoomUser(chatRoomId = chatRoom.id, userIds = listOf(diffUser))

        // when & then
        assertThatThrownBy { workspaceUserService.validUserInWorkspace(user, workspace) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("워크스페이스에 접근할 수 없습니다.")
            .extracting("statusCode")
            .isEqualTo(StatusCode.WORKSPACE_UNREACHABLE)
    }
}
