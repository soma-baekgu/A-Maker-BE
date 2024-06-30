package com.backgu.amaker.fixture

import com.backgu.amaker.workspace.domain.WorkspaceRole
import com.backgu.amaker.workspace.jpa.WorkspaceUserEntity
import com.backgu.amaker.workspace.repository.WorkspaceUserRepository
import java.util.UUID

class WorkspaceUserFixture(
    private val workspaceUserRepository: WorkspaceUserRepository,
) {
    fun testWorkspaceUserSetUp() {
        workspaceUserRepository.saveAll(
            listOf(
                WorkspaceUserEntity(
                    workspaceId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    workspaceRole = WorkspaceRole.LEADER,
                ),
                WorkspaceUserEntity(
                    workspaceId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                    workspaceRole = WorkspaceRole.MEMBER,
                ),
                WorkspaceUserEntity(
                    workspaceId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000003"),
                    workspaceRole = WorkspaceRole.MEMBER,
                ),
                WorkspaceUserEntity(
                    workspaceId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                    workspaceRole = WorkspaceRole.LEADER,
                ),
                WorkspaceUserEntity(
                    workspaceId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    workspaceRole = WorkspaceRole.MEMBER,
                ),
                WorkspaceUserEntity(
                    workspaceId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000003"),
                    workspaceRole = WorkspaceRole.MEMBER,
                ),
            ),
        )
    }
}
