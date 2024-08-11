package com.backgu.amaker.notification.realtime.service

import com.backgu.amaker.domain.realtime.RealTimeServer
import com.backgu.amaker.infra.redis.realtime.repository.RealTimeServerRepository
import org.springframework.stereotype.Service

@Service
class RealTimeService(
    private val realTimeServerRepository: RealTimeServerRepository,
) {
    fun findByIdsToMap(ids: List<String>): Map<String, RealTimeServer> =
        realTimeServerRepository.findAllById(ids).map { it.toDomain() }.associateBy { it.id }

    fun findByIdsToSet(ids: Set<String>): Set<RealTimeServer> = realTimeServerRepository.findAllById(ids).map { it.toDomain() }.toSet()
}
