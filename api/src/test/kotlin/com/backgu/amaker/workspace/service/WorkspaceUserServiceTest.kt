package com.backgu.amaker.workspace.service

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.fixture.WorkspaceFixtureFacade
import com.backgu.amaker.user.domain.User
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
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
        assertDoesNotThrow {
            workspaceUserService.validUserInWorkspace(user, workspace)
        }
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
        assertThrows<EntityNotFoundException> {
            workspaceUserService.validUserInWorkspace(user, workspace)
        }.message.let {
            assertThat(it).isEqualTo("User ${user.id} is not in Workspace ${workspace.id}")
        }
    }
}
