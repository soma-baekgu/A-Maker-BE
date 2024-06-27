package com.backgu.amaker.workspace.domain

import com.backgu.amaker.common.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "workspace")
class Workspace(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var thumbnail: String = "/images/default_thumbnail.png",
) : BaseTimeEntity()
