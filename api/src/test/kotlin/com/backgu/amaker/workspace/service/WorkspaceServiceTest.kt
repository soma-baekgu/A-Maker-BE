package com.backgu.amaker.workspace.service

import com.backgu.amaker.fixture.WorkspaceFixtureFacade
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.workspace.domain.Workspace
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("WorkspaceService 테스트")
@Transactional
@SpringBootTest
class WorkspaceServiceTest {
    @Autowired
    lateinit var fixtures: WorkspaceFixtureFacade

    @Autowired
    lateinit var workspaceService: WorkspaceService

    @Test
    @DisplayName("워크스페이스 탐색 테스트")
    fun getWorkspaceByIdsTest() {
        // given
        val userId = "tester"
        fixtures.user.createPersistedUser(userId)
        val workspace1 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스1")
        val workspace2 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스2")
        val workspace3 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스3")
        val workspace4 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스4")

        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace1.id, leaderId = userId)
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace2.id, leaderId = userId)
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace3.id, leaderId = userId)
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace4.id, leaderId = userId)

        // when
        val result =
            workspaceService.getWorkspaceByIds(listOf(workspace1.id, workspace2.id, workspace3.id, workspace4.id))

        // then
        assertThat(result.size).isEqualTo(4)
    }

    @Test
    @DisplayName("디폴트 워크스페이스 조회 테스트")
    fun getDefaultWorkspaceTest() {
        // given
        val userId = "tester"
        val user: User = fixtures.user.createPersistedUser(userId)
        val workspace1 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스1")
        val workspace2 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스2")
        val workspace3 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스3")
        val workspace4 = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스4")

        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace1.id, leaderId = userId)
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace2.id, leaderId = userId)
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace3.id, leaderId = userId)
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace4.id, leaderId = userId)

        // when
        val result: Workspace = workspaceService.getDefaultWorkspaceByUserId(user)

        // then
        assertThat(result).isNotNull
        assertThat(result.name).isEqualTo("워크스페이스4")
    }

    @Test
    @DisplayName("워크스페이스가 없을 때 디폴트 워크스페이스 조회 테스트")
    fun getDefaultWorkspaceWhenNoWorkspaceTest() {
        // given
        val userId = "tester"
        val user: User = fixtures.user.createPersistedUser(userId)

        // when & then
        assertThatThrownBy { workspaceService.getDefaultWorkspaceByUserId(user) }
            .isInstanceOf(EntityNotFoundException::class.java)
            .hasMessage("Default workspace not found : tester")
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