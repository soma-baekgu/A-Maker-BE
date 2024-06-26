package com.backgu.amaker.workspace.jpa

import com.backgu.amaker.common.jpa.BaseTimeEntity
import com.backgu.amaker.workspace.domain.Workspace
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "Workspace")
@Table(name = "workspace")
class WorkspaceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var thumbnail: String = "/images/default_thumbnail.png",
) : BaseTimeEntity() {
    fun toDomain(): Workspace =
        Workspace(
            id = id,
            name = name,
            thumbnail = thumbnail,
        )

    companion object {
        fun of(workspace: Workspace) =
            WorkspaceEntity(
                id = workspace.id,
                name = workspace.name,
                thumbnail = workspace.thumbnail,
            )
    }
}
