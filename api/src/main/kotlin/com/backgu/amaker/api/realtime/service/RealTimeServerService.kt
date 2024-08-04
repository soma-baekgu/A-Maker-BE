package com.backgu.amaker.api.realtime.service

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

    fun delete(id: Long) = realTimeServerRepository.deleteById(id)

    fun findAll(): List<RealTimeServer> = realTimeServerRepository.findAll().map { it.toDomain() }
}
