package com.backgu.amaker.api.workspace.service

import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.api.fixture.WorkspaceFixtureFacade
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.user.User
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
    @DisplayName("유저가 속한 워크스페이스의 그룹 조회 성공")
    fun getWorkspaceIn() {
        // given
        val userId = "tester"
        val user = fixtures.user.createPersistedUser(userId)
        val workspace = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스1")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace.id, leaderId = userId)

        // when & then
        assertThatCode {
            workspaceUserService.validUserInWorkspace(user, workspace)
        }.doesNotThrowAnyException()
    }

    @Test
    @DisplayName("유저가 속하지 않는 워크스페이스의 그룹 조회 실패")
    fun failGetWorkspaceNotIn() {
        // given
        val userId = "tester"
        val user: User = fixtures.user.createPersistedUser(userId)

        val diffUser = "diff tester"
        fixtures.user.createPersistedUser(diffUser)
        val workspace = fixtures.workspace.createPersistedWorkspace(name = "워크스페이스1")
        fixtures.workspaceUser.createPersistedWorkspaceUser(workspaceId = workspace.id, leaderId = diffUser)

        // when & then
        assertThatThrownBy { workspaceUserService.validUserInWorkspace(user, workspace) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("워크스페이스에 접근할 수 없습니다.")
            .extracting("statusCode")
            .isEqualTo(StatusCode.WORKSPACE_UNREACHABLE)
    }
}
