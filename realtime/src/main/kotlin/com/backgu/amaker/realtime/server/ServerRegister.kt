package com.backgu.amaker.realtime.server

import com.backgu.amaker.domain.realtime.RealTimeServer
import com.backgu.amaker.realtime.server.config.ServerConfig
import com.backgu.amaker.realtime.server.service.RealTimeServerService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class ServerRegister(
    private val realTimeServerService: RealTimeServerService,
    private val serverConfig: ServerConfig,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        realTimeServerService.save(
            RealTimeServer.httpOf(
                id = serverConfig.id,
                address = serverConfig.address,
                port = serverConfig.port,
            ),
        )
    }
}
