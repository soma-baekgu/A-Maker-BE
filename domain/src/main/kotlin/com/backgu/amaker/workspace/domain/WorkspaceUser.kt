package com.backgu.amaker.workspace.domain

import com.backgu.amaker.common.domain.BaseTime

class WorkspaceUser(
    val id: Long = 0L,
    val userId: String,
    val workspaceId: Long,
    var workspaceRole: WorkspaceRole = WorkspaceRole.MEMBER,
) : BaseTime()
