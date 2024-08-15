package com.backgu.amaker.notification.workspace.service

import com.backgu.amaker.domain.session.Session
import com.backgu.amaker.infra.redis.session.workspace.repository.WorkspaceSessionRepository
import org.springframework.stereotype.Service

@Service
class WorkspaceSessionService(
    private val workspaceSessionRepository: WorkspaceSessionRepository,
) {
    fun findByWorkspaceId(workspaceId: Long): List<Session> =
        workspaceSessionRepository.findWorkspaceSessionByWorkspaceId(workspaceId).map { it.toDomain() }
}
