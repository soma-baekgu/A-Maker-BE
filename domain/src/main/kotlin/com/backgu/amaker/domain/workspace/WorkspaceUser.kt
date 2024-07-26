package com.backgu.amaker.domain.workspace

import com.backgu.amaker.domain.common.BaseTime
import com.backgu.amaker.domain.user.User

class WorkspaceUser(
    val id: Long = 0L,
    val userId: String,
    val workspaceId: Long,
    var workspaceRole: WorkspaceRole = WorkspaceRole.MEMBER,
    var status: WorkspaceUserStatus = WorkspaceUserStatus.PENDING,
) : BaseTime() {
    fun activate(): WorkspaceUser {
        this.status = WorkspaceUserStatus.ACTIVE
        return this
    }

    fun isAdmin() = this.workspaceRole == WorkspaceRole.LEADER

    companion object {
        fun makeWorkspaceLeader(
            workspace: Workspace,
            user: User,
        ): WorkspaceUser =
            WorkspaceUser(
                userId = user.id,
                workspaceId = workspace.id,
                workspaceRole = WorkspaceRole.LEADER,
                status = WorkspaceUserStatus.ACTIVE,
            )
    }
}
