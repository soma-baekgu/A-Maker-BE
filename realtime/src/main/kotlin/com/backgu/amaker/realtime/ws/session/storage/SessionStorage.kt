package com.backgu.amaker.realtime.ws.session.storage

interface SessionStorage<W, U, V> {
    fun addSession(
        workspaceId: W,
        userId: U,
        session: V,
    )

    fun removeSession(
        workspaceId: W,
        userId: U,
    )

    fun getSession(
        workspaceId: W,
        userId: U,
    ): V?

    fun getWorkspaceSessions(workspaceId: W): List<V>
}
