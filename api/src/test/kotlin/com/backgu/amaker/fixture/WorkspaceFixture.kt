package com.backgu.amaker.fixture

import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import java.util.UUID

class WorkspaceFixture {
    companion object {
        fun createWorkspaceRequest(userId: UUID) =
            WorkspaceCreateDto(
                userId = userId,
                name = "name",
            )
    }
}
