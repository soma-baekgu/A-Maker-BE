package com.backgu.amaker.notification.realtime.service

import com.backgu.amaker.domain.notifiacation.RealTimeBasedNotification
import com.backgu.amaker.domain.realtime.RealTimeServer
import com.backgu.amaker.notification.realtime.infra.RealTimeClient
import com.backgu.amaker.notification.realtime.infra.dto.IndividualUserRealTimeNotificationRequest
import com.backgu.amaker.notification.realtime.infra.dto.RealTimeNotificationRequest
import org.springframework.stereotype.Service
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Service
class RealTimeCallService {
    fun sendUserRealTimeNotification(
        userIds: List<String>,
        realTimeServer: RealTimeServer,
        notificationEvent: RealTimeBasedNotification,
    ): List<String> {
        val client = createHealthCheckClient(realTimeServer.path())
        return try {
            client.publishEvent(IndividualUserRealTimeNotificationRequest.of(userIds, notificationEvent))
        } catch (e: ResourceAccessException) {
            emptyList<String>()
        }
    }

    fun sendWorkspaceRealTimeNotification(
        workspaceId: Long,
        realTimeServer: RealTimeServer,
        notificationEvent: RealTimeBasedNotification,
    ): List<String> {
        val client = createHealthCheckClient(realTimeServer.path())
        return try {
            client.publishEventToWorkspace(workspaceId, RealTimeNotificationRequest.of(notificationEvent))
        } catch (e: ResourceAccessException) {
            emptyList<String>()
        }
    }

    private fun createHealthCheckClient(baseUrl: String): RealTimeClient {
        val restClient = RestClient.builder().baseUrl(baseUrl).build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(RealTimeClient::class.java)
    }
}
