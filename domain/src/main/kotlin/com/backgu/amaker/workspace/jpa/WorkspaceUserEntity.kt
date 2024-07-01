package com.backgu.amaker.workspace.jpa

import com.backgu.amaker.common.jpa.BaseTimeEntity
import com.backgu.amaker.workspace.domain.WorkspaceRole
import com.backgu.amaker.workspace.domain.WorkspaceUser
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "WorkspaceUser")
@Table(name = "workspace_user")
class WorkspaceUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false)
    val workspaceId: Long,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var workspaceRole: WorkspaceRole = WorkspaceRole.MEMBER,
) : BaseTimeEntity() {
    fun toDomain(): WorkspaceUser =
        WorkspaceUser(
            id = id,
            userId = userId,
            workspaceId = workspaceId,
            workspaceRole = workspaceRole,
        )

    companion object {
        fun of(workspaceUser: WorkspaceUser): WorkspaceUserEntity =
            WorkspaceUserEntity(
                id = workspaceUser.id,
                userId = workspaceUser.userId,
                workspaceId = workspaceUser.workspaceId,
                workspaceRole = workspaceUser.workspaceRole,
            )
    }
}
