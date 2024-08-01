package com.backgu.amaker.api.workspace.dto.response

import com.backgu.amaker.api.workspace.dto.WorkspaceUserDto
import com.backgu.amaker.domain.workspace.WorkspaceRole
import com.backgu.amaker.domain.workspace.WorkspaceUserStatus
import io.swagger.v3.oas.annotations.media.Schema

data class WorkspaceUserResponse(
    @Schema(description = "이메일", example = "abc@example.com")
    val email: String,
    @Schema(description = "워크스페이스 id", example = "1")
    val workspaceId: Long,
    @Schema(description = "워크스페이스 권한(MEMBER, LEADER)", example = "MEMBER")
    var workspaceRole: WorkspaceRole,
    @Schema(description = "워크스페이스 가입 상태(PENDING, ACTIVE)", example = "PENDING")
    var status: WorkspaceUserStatus,
) {
    companion object {
        fun of(workspaceUser: WorkspaceUserDto) =
            WorkspaceUserResponse(
                email = workspaceUser.email,
                workspaceId = workspaceUser.workspaceId,
                workspaceRole = workspaceUser.workspaceRole,
                status = workspaceUser.status,
            )
    }
}
