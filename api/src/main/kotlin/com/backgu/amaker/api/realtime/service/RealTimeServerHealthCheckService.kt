package com.backgu.amaker.api.realtime.service

import com.backgu.amaker.api.config.SystemConfig
import com.backgu.amaker.api.realtime.infra.RealTimeApiClient
import com.backgu.amaker.common.security.jwt.component.JwtComponent
import com.backgu.amaker.domain.user.UserRole
import org.springframework.stereotype.Service
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Service
class RealTimeServerHealthCheckService(
    private val jwtComponent: JwtComponent,
    private val systemConfig: SystemConfig,
) {
    companion object {
        fun buildBearerToken(token: String) = "Bearer $token"
    }

    fun healthCheck(baseUrl: String): Boolean {
        val systemToken = jwtComponent.create(systemConfig.id, UserRole.ADMIN.key)
        val client = createHealthCheckClient(baseUrl)
        return try {
            client.healthCheck(buildBearerToken(systemToken))?.isValid() ?: false
        } catch (e: ResourceAccessException) {
            false
        }
    }

    private fun createHealthCheckClient(baseUrl: String): RealTimeApiClient {
        val restClient = RestClient.builder().baseUrl(baseUrl).build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(RealTimeApiClient::class.java)
    }
}
