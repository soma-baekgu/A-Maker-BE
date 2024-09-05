package com.backgu.amaker.infra.jpa.event.repository

import com.backgu.amaker.infra.jpa.event.entity.ReactionEventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReactionEventRepository : JpaRepository<ReactionEventEntity, Long>
