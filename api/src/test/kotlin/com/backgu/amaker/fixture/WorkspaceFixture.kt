package com.backgu.amaker.fixture

import com.backgu.amaker.workspace.domain.Workspace
import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import com.backgu.amaker.workspace.repository.WorkspaceRepository

class WorkspaceFixture(
    private val workspaceRepository: WorkspaceRepository,
) {
    companion object {
        fun createWorkspaceRequest() =
            WorkspaceCreateDto(
                name = "name",
            )
    }

    fun testWorkspaceSetUp() {
        workspaceRepository.saveAll(
            listOf(
                Workspace(
                    id = 1L,
                    name = "워크스페이스1",
                    thumbnail = "image/thumbnail1.png",
                ),
                Workspace(
                    id = 2L,
                    name = "워크스페이스2",
                    thumbnail = "image/thumbnail2.png",
                ),
            ),
        )
    }
}
