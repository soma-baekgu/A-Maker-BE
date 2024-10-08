package com.backgu.amaker.api.chat.service

import com.backgu.amaker.api.common.container.IntegrationTest
import com.backgu.amaker.api.fixture.ChatFixtureFacade
import com.backgu.amaker.application.chat.service.ChatRoomUserService
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatRoomType
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.Workspace
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("ChatRoomUserService 테스트")
@Transactional
class ChatRoomUserServiceTest : IntegrationTest() {
    @Autowired
    lateinit var chatRoomUserService: ChatRoomUserService

    companion object {
        private lateinit var fixtures: ChatFixtureFacade
        private const val TEST_USER_ID: String = "default-user"

        @JvmStatic
        @BeforeAll
        fun setUp(
            @Autowired fixtures: ChatFixtureFacade,
        ) {
            fixtures.setUp()
            this.fixtures = fixtures
        }
    }

    @Test
    @DisplayName("유저가 속한 채팅방 조회 성공")
    fun getChatRoomIn() {
        // given
        val userId: String = TEST_USER_ID
        val user: User = fixtures.userFixture.createPersistedUser(userId)
        val workspace: Workspace = fixtures.workspaceFixture.createPersistedWorkspace(name = "워크스페이스1")
        fixtures.workspaceUserFixture.createPersistedWorkspaceUser(workspaceId = workspace.id, leaderId = userId)
        val chatRoom: ChatRoom =
            fixtures.chatRoomFixture.createPersistedChatRoom(
                workspaceId = workspace.id,
                chatRoomType = ChatRoomType.DEFAULT,
            )
        fixtures.chatRoomUserFixture.createPersistedChatRoomUser(chatRoomId = chatRoom.id, userIds = listOf(userId))

        // when & then
        assertThatCode {
            chatRoomUserService.validateUserInChatRoom(user, chatRoom)
        }.doesNotThrowAnyException()
    }

    @Test
    @DisplayName("유저가 속하지 않는는 채팅방 조회 실패")
    fun failGetChatRoomNoIn() {
        // given
        val userId: String = TEST_USER_ID
        val user: User = fixtures.userFixture.createPersistedUser(userId)

        val diffUserId = "diff tester"
        val chatRoom = fixtures.setUp(userId = diffUserId)

        // when & then
        assertThatThrownBy { chatRoomUserService.validateUserInChatRoom(user, chatRoom) }
            .isInstanceOf(BusinessException::class.java)
            .extracting("statusCode")
            .isEqualTo(StatusCode.CHAT_ROOM_USER_NOT_FOUND)
    }
}
