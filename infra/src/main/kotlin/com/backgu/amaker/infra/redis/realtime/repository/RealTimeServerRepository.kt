package com.backgu.amaker.infra.redis.realtime.repository

import com.backgu.amaker.infra.redis.realtime.hash.RealTimeServerHash
import org.springframework.data.repository.CrudRepository

interface RealTimeServerRepository : CrudRepository<RealTimeServerHash, String>
