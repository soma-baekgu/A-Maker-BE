package com.backgu.amaker.api.workspace.dto

import com.backgu.amaker.domain.workspace.WorkspaceRole
import com.backgu.amaker.domain.workspace.WorkspaceUser
import com.backgu.amaker.domain.workspace.WorkspaceUserStatus

class WorkspaceUserDto(
    val email: String,
    val workspaceId: Long,
    val workspaceRole: WorkspaceRole,
    val status: WorkspaceUserStatus,
) {
    companion object {
        fun of(
            email: String,
            workspaceUser: WorkspaceUser,
        ): WorkspaceUserDto =
            WorkspaceUserDto(
                email = email,
                workspaceId = workspaceUser.workspaceId,
                workspaceRole = workspaceUser.workspaceRole,
                status = workspaceUser.status,
            )
    }
}
