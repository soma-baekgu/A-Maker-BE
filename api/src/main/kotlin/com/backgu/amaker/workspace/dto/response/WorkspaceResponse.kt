package com.backgu.amaker.workspace.dto.response

import com.backgu.amaker.workspace.dto.WorkspaceDto
import io.swagger.v3.oas.annotations.media.Schema

data class WorkspaceResponse(
    @Schema(description = "워크스페이스 id", example = "1")
    val workspaceId: Long,
    @Schema(description = "워크스페이스 이름", example = "백구팀 워크스페이스")
    val name: String,
    @Schema(description = "워크스페이스 썸네일", example = "https://example.com/thumbnail.jpg")
    val thumbnail: String,
) {
    companion object {
        fun of(workspaceDto: WorkspaceDto) =
            WorkspaceResponse(
                workspaceId = workspaceDto.workspaceId,
                name = workspaceDto.name,
                thumbnail = workspaceDto.thumbnail,
            )
    }
}
