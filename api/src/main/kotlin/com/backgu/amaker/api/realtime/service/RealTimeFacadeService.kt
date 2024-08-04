package com.backgu.amaker.api.realtime.service

import com.backgu.amaker.api.config.SystemConfig
import com.backgu.amaker.api.realtime.dto.RealTimeServerDto
import com.backgu.amaker.domain.realtime.RealTimeServer
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.net.InetAddress

private val logger = KotlinLogging.logger {}

@Service
class RealTimeFacadeService(
    private val realTimeServerService: RealTimeServerService,
    private val realTimeServerIdGeneratorService: RealTimeServerIdGenerator,
    private val realTimeServerHealthCheckService: RealTimeServerHealthCheckService,
) {
    fun connect(
        clientAddress: InetAddress,
        serverPort: Int,
    ): RealTimeServerDto {
        val nextServerId = realTimeServerIdGeneratorService.generateId()

        val realTimeServer =
            realTimeServerService.save(
                RealTimeServer.httpOf(
                    id = nextServerId,
                    address = clientAddress.hostAddress,
                    port = serverPort,
                ),
            )

        return RealTimeServerDto.of(realTimeServer)
    }

    @Scheduled(fixedRate = SystemConfig.REALTIME_SERVER_STATUS_CHECK_INTERVAL)
    fun checkServerStatus() {
        logger.debug { "Checking real-time server status" }
        realTimeServerService
            .findAll()
            .forEach { realTimeServer ->
                if (!realTimeServerHealthCheckService.healthCheck(realTimeServer.path())) {
                    logger.info { "Real-time server is not available: $realTimeServer" }
                    realTimeServerService.delete(realTimeServer.id)
                }
            }
    }
}
