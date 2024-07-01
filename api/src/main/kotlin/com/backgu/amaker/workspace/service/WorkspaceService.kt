package com.backgu.amaker.workspace.service

import com.backgu.amaker.user.domain.User
import com.backgu.amaker.workspace.domain.Workspace
import com.backgu.amaker.workspace.jpa.WorkspaceEntity
import com.backgu.amaker.workspace.repository.WorkspaceRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException
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

    fun getWorkspaceByIds(workspaceIds: List<Long>): List<Workspace> =
        workspaceRepository.findByWorkspaceIds(workspaceIds).map {
            it.toDomain()
        }

    fun getDefaultWorkspaceByUserId(user: User): Workspace =
        workspaceRepository.getDefaultWorkspaceByUserId(user.id)?.toDomain() ?: run {
            logger.error { "Default workspace not found : ${user.id}" }
            throw EntityNotFoundException("Default workspace not found : ${user.id}")
        }
}
