package com.backgu.amaker.event.jpa

import com.backgu.amaker.common.jpa.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.DiscriminatorType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import java.time.LocalDateTime

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "event_type", discriminatorType = DiscriminatorType.STRING)
abstract class EventEntity(
    @Id
    val id: Long,
    @Column(nullable = false)
    val eventTitle: String,
    @Column(nullable = false)
    val deadLine: LocalDateTime,
    @Column(nullable = false)
    val notificationStartTime: LocalDateTime,
    @Column(nullable = false)
    val notificationInterval: Int,
) : BaseTimeEntity()
