package com.backgu.amaker.workspace.service

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.exception.StatusCode
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.workspace.domain.Workspace
import com.backgu.amaker.workspace.domain.WorkspaceUser
import com.backgu.amaker.workspace.domain.WorkspaceUserStatus
import com.backgu.amaker.workspace.jpa.WorkspaceUserEntity
import com.backgu.amaker.workspace.repository.WorkspaceUserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
@Transactional(readOnly = true)
class WorkspaceUserService(
    private val workspaceUserRepository: WorkspaceUserRepository,
) {
    @Transactional
    fun save(workspaceUser: WorkspaceUser): WorkspaceUser = workspaceUserRepository.save(WorkspaceUserEntity.of(workspaceUser)).toDomain()

    fun findWorkspaceIdsByUser(user: User): List<Long> = workspaceUserRepository.findWorkspaceIdsByUserId(user.id)

    fun validUserInWorkspace(
        user: User,
        workspace: Workspace,
    ) {
        if (!workspaceUserRepository.existsByUserIdAndWorkspaceIdAndStatus(user.id, workspace.id, WorkspaceUserStatus.ACTIVE)) {
            throw BusinessException(StatusCode.WORKSPACE_UNREACHABLE)
        }
    }

    fun getWorkspaceUser(
        workspace: Workspace,
        user: User,
    ): WorkspaceUser =
        workspaceUserRepository
            .findByUserIdAndWorkspaceId(workspaceId = workspace.id, userId = user.id)
            ?.toDomain()
            ?: throw BusinessException(StatusCode.WORKSPACE_UNREACHABLE)

    fun verifyUserHasAdminPrivileges(
        workspace: Workspace,
        user: User,
    ) {
        val workspaceUser = getWorkspaceUser(workspace, user)
        if (!workspaceUser.isAdmin()) throw BusinessException(StatusCode.WORKSPACE_UNAUTHORIZED)
    }
}
