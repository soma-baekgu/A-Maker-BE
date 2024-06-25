package com.backgu.amaker.workspace.domain

import com.backgu.amaker.common.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "workspace_user")
class WorkspaceUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val userId: UUID,
    val workspaceId: Long,
    var workspaceRole: WorkspaceRole = WorkspaceRole.MEMBER,
) : BaseTimeEntity()
