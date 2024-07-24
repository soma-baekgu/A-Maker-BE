package com.backgu.amaker.event.repository

import com.backgu.amaker.event.jpa.ReplyEventAssignedUserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyEventAssignedRepository : JpaRepository<ReplyEventAssignedUserEntity, Long>
