package com.backgu.amaker.event.repository

import com.backgu.amaker.event.jpa.ReplyEventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyEventRepository : JpaRepository<ReplyEventEntity, Long>
