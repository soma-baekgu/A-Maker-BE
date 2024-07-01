package com.backgu.amaker.workspace.service

import com.backgu.amaker.user.domain.User
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
}
