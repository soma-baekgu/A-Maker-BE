package com.backgu.amaker.realtime.server.service

import com.backgu.amaker.domain.realtime.RealTimeServer
import com.backgu.amaker.infra.redis.realtime.hash.RealTimeServerHash
import com.backgu.amaker.infra.redis.realtime.repository.RealTimeServerRepository
import org.springframework.stereotype.Service

@Service
class RealTimeServerService(
    private val realTimeServerRepository: RealTimeServerRepository,
) {
    fun save(realTimeServer: RealTimeServer): RealTimeServer =
        realTimeServerRepository.save(RealTimeServerHash.of(realTimeServer)).toDomain()
}
