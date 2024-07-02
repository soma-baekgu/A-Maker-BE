package com.backgu.amaker.workspace.service

import com.backgu.amaker.user.domain.User
import com.backgu.amaker.workspace.domain.Workspace
import com.backgu.amaker.workspace.domain.WorkspaceUser
import com.backgu.amaker.workspace.jpa.WorkspaceUserEntity
import com.backgu.amaker.workspace.repository.WorkspaceUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WorkspaceUserService(
    private val workspaceUserRepository: WorkspaceUserRepository,
) {
    @Transactional
    fun save(workspaceUser: WorkspaceUser): WorkspaceUser = workspaceUserRepository.save(WorkspaceUserEntity.of(workspaceUser)).toDomain()

    fun findWorkspaceIdsByUser(user: User): List<Long> = workspaceUserRepository.findWorkspaceIdsByUserId(user.id)

    @Transactional
    fun getWorkspaceUser(
        workspace: Workspace,
        user: User,
    ): WorkspaceUser =
        workspaceUserRepository
            .findByUserIdAndWorkspaceId(workspaceId = workspace.id, userId = user.id)
            ?.toDomain()
            ?: throw IllegalArgumentException("User is not a member of the workspace")
}
