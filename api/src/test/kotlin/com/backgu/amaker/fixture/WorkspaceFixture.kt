package com.backgu.amaker.fixture

import com.backgu.amaker.workspace.dto.WorkspaceCreateDto

class WorkspaceFixture {
    companion object {
        fun createWorkspaceRequest() =
            WorkspaceCreateDto(
                name = "name",
            )
    }
}
