package com.backgu.amaker.fixture

import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import com.backgu.amaker.workspace.jpa.WorkspaceEntity
import com.backgu.amaker.workspace.repository.WorkspaceRepository
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
        ) = WorkspaceEntity(
            name = name,
            thumbnail = thumbnail,
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
    ) = workspaceRepository
        .save(
            createWorkspace(name = name, thumbnail = thumbnail),
        ).toDomain()

    fun deleteAll() {
        workspaceRepository.deleteAll()
    }
}
