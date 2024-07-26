package com.backgu.amaker.infra.jpa.workspace.entity

import com.backgu.amaker.domain.workspace.WorkspaceRole
import com.backgu.amaker.domain.workspace.WorkspaceUser
import com.backgu.amaker.domain.workspace.WorkspaceUserStatus
import com.backgu.amaker.infra.jpa.common.entity.BaseTimeEntity
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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: WorkspaceUserStatus = WorkspaceUserStatus.PENDING,
) : BaseTimeEntity() {
    fun toDomain(): WorkspaceUser =
        WorkspaceUser(
            id = id,
            userId = userId,
            workspaceId = workspaceId,
            workspaceRole = workspaceRole,
            status = status,
        )

    companion object {
        fun of(workspaceUser: WorkspaceUser): WorkspaceUserEntity =
            WorkspaceUserEntity(
                id = workspaceUser.id,
                userId = workspaceUser.userId,
                workspaceId = workspaceUser.workspaceId,
                workspaceRole = workspaceUser.workspaceRole,
                status = workspaceUser.status,
            )
    }
}
