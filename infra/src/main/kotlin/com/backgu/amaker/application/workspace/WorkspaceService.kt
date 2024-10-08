package com.backgu.amaker.application.workspace

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.infra.jpa.workspace.entity.WorkspaceEntity
import com.backgu.amaker.infra.jpa.workspace.repository.WorkspaceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WorkspaceService(
    private val workspaceRepository: WorkspaceRepository,
) {
    @Transactional
    fun save(workspace: Workspace): Workspace {
        val saveWorkspace = workspaceRepository.save(WorkspaceEntity.of(workspace))
        return saveWorkspace.toDomain()
    }

    @Transactional
    fun updateBelonging(workspace: Workspace) {
        workspaceRepository.updateBelongingWithLimit(
            workspace.id,
            workspace.workspacePlan.belongingLimit,
        ) != 1 &&
            throw BusinessException(StatusCode.INVALID_WORKSPACE_JOIN)
    }

    fun getByIdWithPessimisticLock(id: Long): Workspace =
        workspaceRepository.getLockedWorkspaceById(id)?.toDomain()
            ?: throw BusinessException(StatusCode.WORKSPACE_NOT_FOUND)

    fun getById(id: Long): Workspace =
        workspaceRepository.findByIdOrNull(id)?.toDomain() ?: throw BusinessException(StatusCode.WORKSPACE_NOT_FOUND)

    fun getWorkspaceByIds(workspaceIds: List<Long>): List<Workspace> =
        workspaceRepository.findByWorkspaceIds(workspaceIds).map {
            it.toDomain()
        }

    fun getDefaultWorkspaceByUserId(user: User): Workspace =
        workspaceRepository.getDefaultWorkspaceByUserId(user.id)?.toDomain() ?: run {
            throw BusinessException(StatusCode.WORKSPACE_NOT_FOUND)
        }

    fun getWorkspaceById(workspaceId: Long): Workspace =
        workspaceRepository.findByIdOrNull(workspaceId)?.toDomain()
            ?: throw BusinessException(StatusCode.WORKSPACE_NOT_FOUND)

    fun getWorkspaceIdByChatRoomId(chatRoomId: Long): Long =
        workspaceRepository.getWorkspaceIdByChatRoomId(chatRoomId)
            ?: throw BusinessException(StatusCode.WORKSPACE_NOT_FOUND)
}
