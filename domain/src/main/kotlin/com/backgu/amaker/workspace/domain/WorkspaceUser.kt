package com.backgu.amaker.workspace.domain

import com.backgu.amaker.common.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
    @Column(nullable = false)
    val userId: UUID,
    @Column(nullable = false)
    val workspaceId: Long,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var workspaceRole: WorkspaceRole = WorkspaceRole.MEMBER,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: WorkspaceUserStatus = WorkspaceUserStatus.PENDING,
) : BaseTimeEntity()
