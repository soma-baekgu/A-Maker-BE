package com.backgu.amaker.workspace.dto

class WorkspaceDto(
    val id: Long,
    val name: String,
    val thumbnail: String,
) {
    companion object {
        fun of(workspace: Workspace): WorkspaceDto =
            WorkspaceDto(
                id = workspace.id,
                name = workspace.name,
                thumbnail = workspace.thumbnail,
            )
    }
}
