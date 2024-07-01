package com.backgu.amaker.workspace.dto

import java.util.UUID

data class WorkspacesDto(
    val userId: UUID,
    val workspaces: List<WorkspaceDto>,
)
