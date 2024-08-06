package com.backgu.amaker.domain.workspace

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class WorkspaceSession
    @JsonCreator
    constructor(
        @JsonProperty("id") val id: String,
        @JsonProperty("userId") val userId: String,
        @JsonProperty("workspaceId") val workspaceId: Long,
        @JsonProperty("realtimeId") val realtimeId: String,
    )
