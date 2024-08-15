package com.backgu.amaker.api.workspace.dto

import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.WorkspaceRole
import com.backgu.amaker.domain.workspace.WorkspaceUser
import com.backgu.amaker.domain.workspace.WorkspaceUserStatus

data class WorkspaceUserDto(
    val name: String,
    val email: String,
    val picture: String,
    val workspaceId: Long,
    val workspaceRole: WorkspaceRole,
    val status: WorkspaceUserStatus,
) {
    companion object {
        fun of(
            user: User,
            workspaceUser: WorkspaceUser,
        ): WorkspaceUserDto =
            WorkspaceUserDto(
                name = user.name,
                email = user.email,
                picture = user.picture,
                workspaceId = workspaceUser.workspaceId,
                workspaceRole = workspaceUser.workspaceRole,
                status = workspaceUser.status,
            )
    }
}
