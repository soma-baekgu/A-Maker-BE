package com.backgu.amaker.fixture

import com.backgu.amaker.workspace.domain.WorkspaceRole
import com.backgu.amaker.workspace.domain.WorkspaceUser
import com.backgu.amaker.workspace.jpa.WorkspaceUserEntity
import com.backgu.amaker.workspace.repository.WorkspaceUserRepository
import org.springframework.stereotype.Component

@Component
class WorkspaceUserFixture(
    private val workspaceUserRepository: WorkspaceUserRepository,
) {
    fun createPersistedWorkspaceUser(
        workspaceId: Long,
        leaderId: String,
        memberIds: List<String> = emptyList(),
    ): List<WorkspaceUser> {
        val workspaceUsers: List<WorkspaceUser> =
            listOf(
                workspaceUserRepository
                    .save(
                        WorkspaceUserEntity(
                            workspaceId = workspaceId,
                            userId = leaderId,
                            workspaceRole = WorkspaceRole.LEADER,
                        ),
                    ).toDomain(),
            ) +
                memberIds.map {
                    workspaceUserRepository
                        .save(
                            WorkspaceUserEntity(
                                workspaceId = workspaceId,
                                userId = it,
                                workspaceRole = WorkspaceRole.MEMBER,
                            ),
                        ).toDomain()
                }

        return workspaceUsers
    }

    fun deleteAll() {
        workspaceUserRepository.deleteAll()
    }
}
