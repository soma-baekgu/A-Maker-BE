package com.backgu.amaker.infra.jpa.event.repository

import com.backgu.amaker.infra.jpa.event.entity.TaskEventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TaskEventRepository : JpaRepository<TaskEventEntity, Long>
