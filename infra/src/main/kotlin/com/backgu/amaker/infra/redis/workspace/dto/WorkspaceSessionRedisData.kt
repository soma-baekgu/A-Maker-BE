package com.backgu.amaker.infra.redis.workspace.dto

import com.backgu.amaker.domain.workspace.WorkspaceSession
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class WorkspaceSessionRedisData
    @JsonCreator
    constructor(
        @JsonProperty("id") val id: String,
        @JsonProperty("userId") val userId: String,
        @JsonProperty("workspaceId") val workspaceId: Long,
        @JsonProperty("realtimeId") val realtimeId: Long,
    ) {
        companion object {
            fun of(workspaceSession: WorkspaceSession) =
                WorkspaceSessionRedisData(
                    workspaceSession.id,
                    workspaceSession.userId,
                    workspaceSession.workspaceId,
                    workspaceSession.realtimeId,
                )
        }

        fun toDomain() = WorkspaceSession(id, userId, workspaceId, realtimeId)
    }
