package com.backgu.amaker.realtime.session.session

import com.backgu.amaker.domain.session.Session

class RealTimeSession<T>(
    val id: String,
    val userId: String,
    val workspaceId: Long,
    val realTimeId: String,
    val session: T,
) {
    companion object {
        const val WORKSPACE_USER_SESSION_LIMIT = 20
    }

    fun toDomain() = Session(id, userId, workspaceId, realTimeId)
}
