package com.backgu.amaker.workspace.dto

import com.backgu.amaker.workspace.domain.WorkspaceUser
import io.swagger.v3.oas.annotations.media.Schema

class WorkspaceUserDto(
    @Schema(description = "워크스페이스 ID", example = "231")
    val workspaceId: Long,
    @Schema(description = "워크스페이스 권한", example = "Leader")
    val workspaceRole: String,
    @Schema(description = "워크스페이스 상태", example = "ACTIVE")
    val status: String,
) {
    companion object {
        fun of(workspaceUser: WorkspaceUser): WorkspaceUserDto =
            WorkspaceUserDto(
                workspaceId = workspaceUser.workspaceId,
                workspaceRole = workspaceUser.workspaceRole.value,
                status = workspaceUser.status.value,
            )
    }
}
