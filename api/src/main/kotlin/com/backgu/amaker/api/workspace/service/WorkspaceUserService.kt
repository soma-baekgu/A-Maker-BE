package com.backgu.amaker.api.workspace.service

import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.api.common.exception.StatusCode
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.domain.workspace.WorkspaceUser
import com.backgu.amaker.domain.workspace.WorkspaceUserStatus
import com.backgu.amaker.infra.jpa.workspace.entity.WorkspaceUserEntity
import com.backgu.amaker.infra.jpa.workspace.repository.WorkspaceUserRepository
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
        if (!workspaceUserRepository.existsByUserIdAndWorkspaceIdAndStatus(
                user.id,
                workspace.id,
                WorkspaceUserStatus.ACTIVE,
            )
        ) {
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

    fun validByUserIdAndChatIdInWorkspace(
        userId: String,
        chatId: Long,
    ) {
        if (!workspaceUserRepository.existsByUserIdAndChatIdInWorkspace(userId, chatId)) {
            throw BusinessException(StatusCode.WORKSPACE_UNREACHABLE)
        }
    }
}
