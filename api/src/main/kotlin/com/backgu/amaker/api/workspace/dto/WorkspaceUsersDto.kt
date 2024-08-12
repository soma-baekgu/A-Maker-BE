package com.backgu.amaker.api.workspace.dto

data class WorkspaceUsersDto(
    val workspaceId: Long,
    val name: String,
    val thumbnail: String,
    val users: List<WorkspaceUserDto>,
) {
    companion object {
        fun of(
            workspaceId: Long,
            name: String,
            thumbnail: String,
            users: List<WorkspaceUserDto>,
        ): WorkspaceUsersDto =
            WorkspaceUsersDto(
                workspaceId = workspaceId,
                name = name,
                thumbnail = thumbnail,
                users = users,
            )
    }
}
