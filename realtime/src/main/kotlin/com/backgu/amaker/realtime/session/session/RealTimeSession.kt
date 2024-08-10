package com.backgu.amaker.realtime.session.session

import com.backgu.amaker.domain.session.Session

class RealTimeSession<T>(
    val id: String,
    val userId: String,
    val workspaceId: Long,
    val realTimeId: String,
    val session: T,
) {
    fun toDomain() = Session(id, userId, workspaceId, realTimeId)
}
