package com.backgu.amaker.workspace.service

import com.backgu.amaker.chat.repository.ChatRoomRepository
import com.backgu.amaker.chat.repository.ChatRoomUserRepository
import com.backgu.amaker.fixture.UserFixture
import com.backgu.amaker.fixture.WorkspaceFixture.Companion.createWorkspaceRequest
import com.backgu.amaker.user.repository.UserRepository
import com.backgu.amaker.workspace.repository.WorkspaceRepository
import com.backgu.amaker.workspace.repository.WorkspaceUserRepository
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID
import kotlin.test.Test

@DisplayName("WorkspaceService 테스트")
@SpringBootTest
class WorkspaceServiceTest {
    @Autowired
    lateinit var workspaceService: WorkspaceService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var workspaceRepository: WorkspaceRepository

    @Autowired
    lateinit var workspaceUserRepository: WorkspaceUserRepository

    @Autowired
    lateinit var chatRoomRepository: ChatRoomRepository

    @Autowired
    lateinit var chatRoomUserRepository: ChatRoomUserRepository

    @Test
    @DisplayName("워크 스페이스 생성 테스트")
    fun createWorkspace() {
        // given
        val request = createWorkspaceRequest()

        // when
        val result = workspaceService.createWorkspace(UserFixture.defaultUserId, request)

        // then
        assertThat(result).isNotNull

        val workspace = workspaceRepository.findByIdOrNull(result)!!
        assertThat(workspace).isNotNull
        assertThat(workspace.name).isEqualTo(request.name)

        val workspaceUserCount = workspaceUserRepository.count()
        assertThat(workspaceUserCount).isOne

        val chatRoomCount = chatRoomRepository.count()
        assertThat(chatRoomCount).isOne

        val chatRoomUserCount = chatRoomUserRepository.count()
        assertThat(chatRoomUserCount).isOne
    }

    @Test
    @DisplayName("유저를 찾을 수 없을 때 워크스페이스 생성 실패")
    fun createWorkspace_UserNotFound() {
        // given
        val request = createWorkspaceRequest()

        // when & then
        assertThrows<EntityNotFoundException> {
            workspaceService.createWorkspace(UUID.randomUUID(), request)
        }
    }

    @BeforeEach
    fun setUp() {
        userRepository.save(UserFixture.createUser(userId = UserFixture.defaultUserId))
    }

    @AfterEach
    fun tearDown() {
        chatRoomUserRepository.deleteAll()
        chatRoomRepository.deleteAll()
        workspaceUserRepository.deleteAll()
        workspaceRepository.deleteAll()
        userRepository.deleteAll()
    }
}
