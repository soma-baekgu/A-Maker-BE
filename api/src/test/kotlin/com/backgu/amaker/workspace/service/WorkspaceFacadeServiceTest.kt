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

@DisplayName("WorkspaceFacadeService 테스트")
@Transactional
@SpringBootTest
class WorkspaceFacadeServiceTest {
    @Autowired
    lateinit var workspaceFacadeService: WorkspaceFacadeService

    @Test
    @DisplayName("워크 스페이스 생성 테스트")
    fun createWorkspace() {
        // given
        val request = createWorkspaceRequest()

        // when
        val result = workspaceFacadeService.createWorkspace(UserFixture.defaultUserId, request)

        // then
        assertThat(result.id).isEqualTo(3L)
    }

    @Test
    @DisplayName("유저의 워크스페이스들 조회")
    fun findWorkspaces() {
        // given
        val userId = UserFixture.defaultUserId

        // when
        val result = workspaceFacadeService.findWorkspaces(userId)

        // then
        assertThat(result.userId).isEqualTo(userId)
        assertThat(result.workspaces.size).isEqualTo(2)
    }

    @Test
    @DisplayName("유저의 기본 워크스페이스 조회")
    fun findDefaultWorkspace() {
        // given
        val userId = UserFixture.defaultUserId

        // when
        val result = workspaceFacadeService.getDefaultWorkspace(userId)

        // then
        assertThat(result.id).isEqualTo(2L)
        assertThat(result.name).isEqualTo("워크스페이스2")
    }

    @Test
    @DisplayName("기본 워크스페이스를 찾을 수 없을 때 실패")
    fun failFindDefaultWorkspace() {
        // given
        val userId = UUID.fromString("00000000-0000-0000-0000-000000000004")

        // when & then
        assertThrows<EntityNotFoundException> {
            workspaceFacadeService.getDefaultWorkspace(userId)
        }.message.let {
            assertThat(it).isEqualTo("Default workspace not found : $userId")
        }
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
