package com.backgu.amaker.fixture

import com.backgu.amaker.workspace.domain.WorkspaceRole
import com.backgu.amaker.workspace.domain.WorkspaceUser
import com.backgu.amaker.workspace.repository.WorkspaceUserRepository
import java.util.UUID

class WorkspaceUserFixture(
    private val workspaceUserRepository: WorkspaceUserRepository,
) {
    fun testWorkspaceUserSetUp() {
        workspaceUserRepository.saveAll(
            listOf(
                WorkspaceUser(
                    workspaceId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    workspaceRole = WorkspaceRole.LEADER,
                ),
                WorkspaceUser(
                    workspaceId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                    workspaceRole = WorkspaceRole.MEMBER,
                ),
                WorkspaceUser(
                    workspaceId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000003"),
                    workspaceRole = WorkspaceRole.MEMBER,
                ),
                WorkspaceUser(
                    workspaceId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                    workspaceRole = WorkspaceRole.LEADER,
                ),
                WorkspaceUser(
                    workspaceId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    workspaceRole = WorkspaceRole.MEMBER,
                ),
                WorkspaceUser(
                    workspaceId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000003"),
                    workspaceRole = WorkspaceRole.MEMBER,
                ),
            ),
        )
    }
}
