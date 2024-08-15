package com.backgu.amaker.api.workspace.dto.response

import com.backgu.amaker.api.workspace.dto.WorkspaceUsersDto
import io.swagger.v3.oas.annotations.media.Schema

data class WorkspaceUsersResponse(
    @Schema(description = "워크스페이스 id", example = "1")
    val workspaceId: Long,
    @Schema(description = "워크스페이스 이름", example = "백구팀 워크스페이스")
    val name: String,
    @Schema(description = "워크스페이스 썸네일", example = "https://example.com/thumbnail.jpg")
    val thumbnail: String,
    @Schema(description = "워크스페이스 멤버들")
    val users: List<WorkspaceUserResponse>,
) {
    companion object {
        fun of(workspaceUsersDto: WorkspaceUsersDto): WorkspaceUsersResponse =
            WorkspaceUsersResponse(
                workspaceId = workspaceUsersDto.workspaceId,
                name = workspaceUsersDto.name,
                thumbnail = workspaceUsersDto.thumbnail,
                users = workspaceUsersDto.users.map { WorkspaceUserResponse.of(it) },
            )
    }
}
