package com.backgu.amaker.api.fixture

import com.backgu.amaker.api.workspace.dto.WorkspaceCreateDto
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.domain.workspace.WorkspacePlan
import com.backgu.amaker.infra.jpa.workspace.entity.WorkspaceEntity
import com.backgu.amaker.infra.jpa.workspace.repository.WorkspaceRepository
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong

@Component
class WorkspaceFixture(
    private val workspaceRepository: WorkspaceRepository,
) {
    companion object {
        private var idValue: AtomicLong = AtomicLong(0L)

        fun createWorkspace(
            id: Long = idValue.incrementAndGet(),
            name: String = nameBuilder(id),
            thumbnail: String = thumbnailBuilder(id),
            plan: WorkspacePlan = WorkspacePlan.BASIC,
        ) = WorkspaceEntity(
            name = name,
            thumbnail = thumbnail,
            workspacePlan = plan,
        )

        fun createWorkspaceRequest(
            name: String = "default-test",
            inviteesEmails: Set<String> = emptySet(),
        ) = WorkspaceCreateDto(
            name = name,
            inviteesEmails = inviteesEmails,
        )

        private fun nameBuilder(id: Any): String = "name-$id"

        private fun thumbnailBuilder(id: Any): String = "http://server/thumbnail-$id"
    }

    fun createPersistedWorkspace(
        id: Long = idValue.incrementAndGet(),
        name: String = nameBuilder(id),
        thumbnail: String = thumbnailBuilder(id),
        plan: WorkspacePlan = WorkspacePlan.BASIC,
    ) = workspaceRepository
        .save(
            createWorkspace(name = name, thumbnail = thumbnail, plan = plan),
        ).toDomain()

    fun save(workspace: Workspace) {
        workspaceRepository.save(WorkspaceEntity.of(workspace))
    }

    fun deleteAll() {
        workspaceRepository.deleteAll()
    }
}
