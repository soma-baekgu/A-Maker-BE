package com.backgu.amaker.domain.workspace

data class WorkspaceSession(
    val id: String,
    val userId: String,
    val workspaceId: Long,
    val realtimeId: String,
)
