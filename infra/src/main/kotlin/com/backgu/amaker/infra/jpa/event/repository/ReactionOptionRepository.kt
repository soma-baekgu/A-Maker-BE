package com.backgu.amaker.infra.jpa.event.repository

import com.backgu.amaker.infra.jpa.event.entity.ReactionOptionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReactionOptionRepository : JpaRepository<ReactionOptionEntity, Long>
