package com.backgu.amaker.fixture

import com.backgu.amaker.workspace.domain.WorkspaceRole
import com.backgu.amaker.workspace.domain.WorkspaceUser
import com.backgu.amaker.workspace.domain.WorkspaceUserStatus
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
        workspaceUserStatus: WorkspaceUserStatus = WorkspaceUserStatus.ACTIVE,
    ): List<WorkspaceUser> =
        this.createPersistedWorkspaceLeader(workspaceId, leaderId, workspaceUserStatus) +
            this.createPersistedWorkspaceMember(workspaceId, memberIds, workspaceUserStatus)

    fun createPersistedWorkspaceLeader(
        workspaceId: Long,
        leaderId: String,
        workspaceUserStatus: WorkspaceUserStatus = WorkspaceUserStatus.ACTIVE,
    ): List<WorkspaceUser> =
        listOf(
            workspaceUserRepository
                .save(
                    WorkspaceUserEntity(
                        workspaceId = workspaceId,
                        userId = leaderId,
                        workspaceRole = WorkspaceRole.LEADER,
                        status = workspaceUserStatus,
                    ),
                ).toDomain(),
        )

    fun createPersistedWorkspaceMember(
        workspaceId: Long,
        memberIds: List<String> = emptyList(),
        workspaceUserStatus: WorkspaceUserStatus = WorkspaceUserStatus.ACTIVE,
    ): List<WorkspaceUser> =
        memberIds.map {
            workspaceUserRepository
                .save(
                    WorkspaceUserEntity(
                        workspaceId = workspaceId,
                        userId = it,
                        workspaceRole = WorkspaceRole.MEMBER,
                        status = workspaceUserStatus,
                    ),
                ).toDomain()
        }

    fun deleteAll() {
        workspaceUserRepository.deleteAll()
    }
}
