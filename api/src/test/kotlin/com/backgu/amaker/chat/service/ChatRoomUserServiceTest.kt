package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.fixture.WorkspaceFixtureFacade
import com.backgu.amaker.user.domain.User
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("ChatRoomUserService 테스트")
@Transactional
@SpringBootTest
class ChatRoomUserServiceTest {
    @Autowired
    lateinit var chatRoomUserService: ChatRoomUserService

    @Autowired
    lateinit var fixtures: WorkspaceFixtureFacade

    @Test
    @DisplayName("유저가 속한 채팅방 조회 성공")
    fun getChatRoomIn() {
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
            chatRoomUserService.validateUserInChatRoom(user, chatRoom)
        }.doesNotThrowAnyException()
    }

    @Test
    @DisplayName("유저가 속하지 않는는 채팅방 조회 실패")
    fun failGetChatRoomNoIn() {
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
        assertThatThrownBy { chatRoomUserService.validateUserInChatRoom(user, chatRoom) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage("User ${user.id} is not in ChatRoom ${chatRoom.id}")
    }
}
