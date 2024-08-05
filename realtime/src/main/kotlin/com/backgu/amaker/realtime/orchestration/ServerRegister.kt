package com.backgu.amaker.realtime.orchestration

import com.backgu.amaker.common.http.response.ApiSuccess
import com.backgu.amaker.common.security.jwt.component.JwtComponent
import com.backgu.amaker.domain.user.UserRole
import com.backgu.amaker.realtime.orchestration.config.OrchestrationConfig
import com.backgu.amaker.realtime.orchestration.dto.ServerInformation
import com.backgu.amaker.realtime.orchestration.infra.OrchestrationClient
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class ServerRegister(
    private val orchestrationClient: OrchestrationClient,
    private val jwtComponent: JwtComponent,
    private val orchestrationConfig: OrchestrationConfig,
) : ApplicationRunner {
    companion object {
        var serverId: Long = 0L
            internal set

        fun getBearerToken(token: String): String = "Bearer $token"
    }

    override fun run(args: ApplicationArguments?) {
        val systemToken = jwtComponent.create(orchestrationConfig.systemUserId, UserRole.ADMIN.key)
        val registerThisServer: ApiSuccess<ServerInformation> =
            orchestrationClient.registerThisServer(getBearerToken(systemToken), orchestrationConfig.openPort)
                ?: throw Error("Failed to register this server")
        serverId = registerThisServer.data.id
    }
}
