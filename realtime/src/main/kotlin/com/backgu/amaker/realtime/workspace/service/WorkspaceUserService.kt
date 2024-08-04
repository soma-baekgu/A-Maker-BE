package com.backgu.amaker.realtime.workspace.service

import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.workspace.WorkspaceUserStatus
import com.backgu.amaker.infra.jpa.workspace.repository.WorkspaceUserRepository
import com.backgu.amaker.realtime.common.excpetion.RealTimeException
import org.springframework.stereotype.Service

@Service
class WorkspaceUserService(
    private val workspaceUserRepository: WorkspaceUserRepository,
) {
    fun checkUserBelongToWorkspace(
        userId: String,
        workspaceId: Long,
    ) {
        if (!workspaceUserRepository.existsByUserIdAndWorkspaceIdAndStatus(
                userId,
                workspaceId,
                WorkspaceUserStatus.ACTIVE,
            )
        ) {
            throw RealTimeException(StatusCode.WORKSPACE_UNREACHABLE)
        }
    }
}
