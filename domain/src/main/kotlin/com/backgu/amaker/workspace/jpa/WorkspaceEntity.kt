package com.backgu.amaker.workspace.jpa

import com.backgu.amaker.common.jpa.BaseTimeEntity
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
) : BaseTimeEntity()
