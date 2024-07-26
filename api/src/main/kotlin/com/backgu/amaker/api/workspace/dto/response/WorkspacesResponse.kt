package com.backgu.amaker.api.workspace.dto.response

import com.backgu.amaker.api.workspace.dto.WorkspacesDto
import io.swagger.v3.oas.annotations.media.Schema

data class WorkspacesResponse(
    @Schema(description = "유저 id", example = "550e8400-e29b-41d4-a716-446655440000")
    val userId: String,
    @Schema(
        description = "유저가 참여하고 있는 워크스페이스들",
        example = "[{\"workspaceId\":1,\"name\":\"백구팀 워크스페이스\",\"thumbnail\":\"https://example.com/thumbnail.jpg\"}]",
    )
    val workspaces: List<WorkspaceResponse>,
) {
    companion object {
        fun of(workspaces: WorkspacesDto) =
            WorkspacesResponse(
                userId = workspaces.userId,
                workspaces = workspaces.workspaces.map { WorkspaceResponse.of(it) },
            )
    }
}
