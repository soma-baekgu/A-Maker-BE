package com.backgu.amaker.infra.jpa.notification

import com.backgu.amaker.infra.jpa.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "Notification")
@Table(name = "notification")
class NotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    val title: String,
    val content: String,
    @Column(nullable = false)
    val userId: Long,
) : BaseTimeEntity()
