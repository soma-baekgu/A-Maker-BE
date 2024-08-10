package com.backgu.amaker.domain.session

data class Session(
    val id: String,
    val userId: String,
    val workspaceId: Long,
    val realtimeId: String,
) {
    fun isBelongToServer(serverId: String): Boolean = realtimeId.startsWith(serverId)
}
