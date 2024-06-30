package com.backgu.amaker.workspace.dto

import com.backgu.amaker.workspace.domain.Workspace

class WorkspaceDto(
    val id: Long,
    val name: String,
    val thumbnail: String,
) {
    companion object {
        fun of(workspace: Workspace): WorkspaceDto {
            return WorkspaceDto(
                id = workspace.id,
                name = workspace.name,
                thumbnail = workspace.thumbnail,
            )
        }
    }
}
