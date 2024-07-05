package com.backgu.amaker.workspace.service

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.exception.StatusCode
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.workspace.domain.Workspace
import com.backgu.amaker.workspace.jpa.WorkspaceEntity
import com.backgu.amaker.workspace.repository.WorkspaceRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

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

    fun getById(id: Long): Workspace =
        workspaceRepository.findByIdOrNull(id)?.toDomain() ?: run {
            logger.error { "Workspace not found : $id" }
            throw BusinessException(StatusCode.WORKSPACE_NOT_FOUND)
        }

    fun getWorkspaceByIds(workspaceIds: List<Long>): List<Workspace> =
        workspaceRepository.findByWorkspaceIds(workspaceIds).map {
            it.toDomain()
        }

    fun getDefaultWorkspaceByUserId(user: User): Workspace =
        workspaceRepository.getDefaultWorkspaceByUserId(user.id)?.toDomain() ?: run {
            logger.error { "Default workspace not found : ${user.id}" }
            throw BusinessException(StatusCode.WORKSPACE_NOT_FOUND)
        }

    fun getWorkspaceById(workspaceId: Long): Workspace =
        // TODO : 로깅
        workspaceRepository.findByIdOrNull(workspaceId)?.toDomain() ?: run {
            logger.error { "Workspace not found : $workspaceId" }
            throw BusinessException(StatusCode.WORKSPACE_NOT_FOUND)
        }
}
