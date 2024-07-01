package com.backgu.amaker.workspace.domain

import com.backgu.amaker.common.domain.BaseTime
import java.util.UUID

class WorkspaceUser(
    val id: Long = 0L,
    val userId: UUID,
    val workspaceId: Long,
    var workspaceRole: WorkspaceRole = WorkspaceRole.MEMBER,
) : BaseTime()
