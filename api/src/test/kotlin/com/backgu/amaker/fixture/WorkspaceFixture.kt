package com.backgu.amaker.fixture

import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import com.backgu.amaker.workspace.jpa.WorkspaceEntity
import com.backgu.amaker.workspace.repository.WorkspaceRepository
import org.springframework.stereotype.Component

@Component
class WorkspaceFixture(
    private val workspaceRepository: WorkspaceRepository,
) {
    companion object {
        fun createWorkspace(
            id: Long = 0L,
            name: String?,
            thumbnail: String?,
        ) = WorkspaceEntity(
            id = id,
            name = name ?: nameBuilder(id),
            thumbnail = thumbnail ?: thumbnailBuilder(id),
        )

        fun createWorkspaceRequest(name: String = "default-test") =
            WorkspaceCreateDto(
                name = name,
            )

        private fun nameBuilder(id: Any): String = "name-$id"

        private fun thumbnailBuilder(id: Any): String = "http://server/thumbnail-$id"
    }

    fun createPersistedWorkspace(
        name: String? = null,
        thumbnail: String? = null,
    ) = workspaceRepository
        .save(
            createWorkspace(name = name, thumbnail = thumbnail),
        ).toDomain()
}
