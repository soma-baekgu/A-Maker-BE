package com.backgu.amaker.infra.jpa.notification.repository

import com.backgu.amaker.infra.jpa.notification.entity.EventNotificationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventNotificationRepository : JpaRepository<EventNotificationEntity, Long>
