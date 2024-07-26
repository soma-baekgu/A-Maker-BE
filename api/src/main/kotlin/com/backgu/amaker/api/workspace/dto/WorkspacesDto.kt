package com.backgu.amaker.api.workspace.dto

data class WorkspacesDto(
    val userId: String,
    val workspaces: List<WorkspaceDto>,
)
