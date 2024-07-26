package com.backgu.amaker.domain.workspace

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("WorkspaceUser 테스트")
class WorkspaceUserTest {
    @Test
    @DisplayName("워크스페이스 리더 생성")
    fun makeWorkspaceLeader() {
        // given
        val workspaceUser =
            WorkspaceUser(
                userId = "member",
                workspaceId = 1,
                workspaceRole = WorkspaceRole.MEMBER,
                status = WorkspaceUserStatus.PENDING,
            )

        // when
        workspaceUser.activate()

        // then
        assertThat(workspaceUser.status).isEqualTo(WorkspaceUserStatus.ACTIVE)
    }
}
