package com.backgu.amaker.workspace.dto

import com.backgu.amaker.workspace.domain.Workspace

data class WorkspaceDto(
    val workspaceId: Long,
    val name: String,
    val thumbnail: String,
) {
    companion object {
        fun of(workspace: Workspace): WorkspaceDto =
            WorkspaceDto(
                workspaceId = workspace.id,
                name = workspace.name,
                thumbnail = workspace.thumbnail,
            )
    }
}
