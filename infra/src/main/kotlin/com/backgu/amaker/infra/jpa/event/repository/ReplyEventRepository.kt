package com.backgu.amaker.infra.jpa.event.repository

import com.backgu.amaker.infra.jpa.event.entity.ReplyEventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyEventRepository : JpaRepository<ReplyEventEntity, Long>
