package com.backgu.amaker.event.repository

import com.backgu.amaker.event.jpa.EventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<EventEntity, Long>
