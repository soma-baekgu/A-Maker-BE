package com.backgu.amaker.realtime.workspace.session

import com.backgu.amaker.domain.workspace.WorkspaceSession

class WorkspaceWebSocketSession<T>(
    val id: String,
    val userId: String,
    val workspaceId: Long,
    val realTimeId: Long,
    val session: T,
) {
    fun toDomain() = WorkspaceSession(id, userId, workspaceId, realTimeId)
}
