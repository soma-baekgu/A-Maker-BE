package com.backgu.amaker.infra.jpa.workspace.entity

import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.domain.workspace.WorkspacePlan
import com.backgu.amaker.infra.jpa.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
    @Column(nullable = false)
    var belongingNumber: Int = 0,
    @Enumerated(EnumType.STRING)
    var workspacePlan: WorkspacePlan = WorkspacePlan.BASIC,
) : BaseTimeEntity() {
    fun toDomain(): Workspace =
        Workspace(
            id = id,
            name = name,
            thumbnail = thumbnail,
            belongingNumber = belongingNumber,
            workspacePlan = workspacePlan,
        )

    companion object {
        fun of(workspace: Workspace) =
            WorkspaceEntity(
                id = workspace.id,
                name = workspace.name,
                thumbnail = workspace.thumbnail,
                belongingNumber = workspace.belongingNumber,
                workspacePlan = workspace.workspacePlan,
            )
    }

    override fun toString(): String =
        "WorkspaceEntity(id=$id, name='$name', thumbnail='$thumbnail', belongingNumber=$belongingNumber, workspacePlan=$workspacePlan)"
}
