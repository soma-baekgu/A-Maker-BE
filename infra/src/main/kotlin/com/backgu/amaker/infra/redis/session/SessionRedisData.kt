package com.backgu.amaker.infra.redis.session

import com.backgu.amaker.domain.session.Session
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class SessionRedisData
    @JsonCreator
    constructor(
        @JsonProperty("id") val id: String,
        @JsonProperty("userId") val userId: String,
        @JsonProperty("workspaceId") val workspaceId: Long,
        @JsonProperty("realtimeId") val realtimeId: String,
    ) {
        companion object {
            fun of(session: Session) =
                SessionRedisData(
                    session.id,
                    session.userId,
                    session.workspaceId,
                    session.realtimeId,
                )
        }

        fun toDomain() = Session(id, userId, workspaceId, realtimeId)
    }
