package com.backgu.amaker.workspace.dto

import java.util.UUID

class WorkspacesDto(
    val userId: UUID,
    val workspaces: List<WorkspaceDto>,
)
