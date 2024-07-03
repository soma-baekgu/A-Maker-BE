package com.backgu.amaker.workspace.dto

import com.backgu.amaker.workspace.domain.WorkspaceUser
import com.backgu.amaker.workspace.domain.WorkspaceUserStatus

class WorkspaceUserDto(
    val userId: String,
    val workspaceId: Long,
    val workspaceRole: String,
    val status: WorkspaceUserStatus,
) {
    companion object {
        fun of(workspaceUser: WorkspaceUser): WorkspaceUserDto =
            WorkspaceUserDto(
                userId = workspaceUser.userId,
                workspaceId = workspaceUser.workspaceId,
                workspaceRole = workspaceUser.workspaceRole.value,
                status = workspaceUser.status,
            )
    }
}
