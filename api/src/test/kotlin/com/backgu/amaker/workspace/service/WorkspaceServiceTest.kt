package com.backgu.amaker.workspace.service

import com.backgu.amaker.chat.repository.ChatRoomRepository
import com.backgu.amaker.chat.repository.ChatRoomUserRepository
import com.backgu.amaker.fixture.ChatRoomFixture
import com.backgu.amaker.fixture.ChatRoomUserFixture
import com.backgu.amaker.fixture.UserFixture
import com.backgu.amaker.fixture.WorkspaceFixture
import com.backgu.amaker.fixture.WorkspaceFixture.Companion.createWorkspaceRequest
import com.backgu.amaker.fixture.WorkspaceUserFixture
import com.backgu.amaker.user.repository.UserRepository
import com.backgu.amaker.workspace.repository.WorkspaceRepository
import com.backgu.amaker.workspace.repository.WorkspaceUserRepository
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import kotlin.test.Test

@DisplayName("WorkspaceService 테스트")
@Transactional
@SpringBootTest
class WorkspaceServiceTest {
    @Autowired
    lateinit var workspaceService: WorkspaceService

    @Test
    @DisplayName("워크 스페이스 생성 테스트")
    fun createWorkspace() {
        // given
        val request = createWorkspaceRequest(UserFixture.defaultUserId)

        // when
        val result = workspaceService.createWorkspace(request)

        // then
        assertThat(result).isEqualTo(3L)
    }

    @Test
    @DisplayName("유저를 찾을 수 없을 때 워크스페이스 생성 실패")
    fun createWorkspace_UserNotFound() {
        // given
        val request = createWorkspaceRequest(UUID.randomUUID())

        // when & then
        assertThrows<EntityNotFoundException> {
            workspaceService.createWorkspace(request)
        }
    }

    @Test
    @DisplayName("유저의 워크스페이스 조회")
    fun findWorkspaces() {
        // given
        val userId = UserFixture.defaultUserId

        // when
        val result = workspaceService.findWorkspaces(userId)

        // then
        assertThat(result.userId).isEqualTo(userId)
        assertThat(result.workspaces.size).isEqualTo(2)
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp(
            @Autowired userRepository: UserRepository,
            @Autowired workspaceRepository: WorkspaceRepository,
            @Autowired workspaceUserRepository: WorkspaceUserRepository,
            @Autowired chatRoomRepository: ChatRoomRepository,
            @Autowired chatRoomUserRepository: ChatRoomUserRepository,
        ) {
            UserFixture(userRepository).testUserSetUp()
            WorkspaceFixture(workspaceRepository).testWorkspaceSetUp()
            WorkspaceUserFixture(workspaceUserRepository).testWorkspaceUserSetUp()
            ChatRoomFixture(chatRoomRepository).testChatRoomSetUp()
            ChatRoomUserFixture(chatRoomUserRepository).testChatRoomUserSetUp()
        }
    }
}
