package com.backgu.amaker.workspace.dto

import java.util.UUID

data class WorkspaceCreateDto(
    val userId: UUID,
    val name: String,
)
