package com.backgu.amaker.fixture

import com.backgu.amaker.workspace.dto.WorkspaceCreate
import com.backgu.amaker.workspace.jpa.WorkspaceEntity
import com.backgu.amaker.workspace.repository.WorkspaceRepository

class WorkspaceFixture(
    private val workspaceRepository: WorkspaceRepository,
) {
    companion object {
        fun createWorkspaceRequest() =
            WorkspaceCreate(
                name = "name",
            )
    }

    fun testWorkspaceSetUp() {
        workspaceRepository.saveAll(
            listOf(
                WorkspaceEntity(
                    id = 1L,
                    name = "워크스페이스1",
                    thumbnail = "image/thumbnail1.png",
                ),
                WorkspaceEntity(
                    id = 2L,
                    name = "워크스페이스2",
                    thumbnail = "image/thumbnail2.png",
                ),
            ),
        )
    }
}
