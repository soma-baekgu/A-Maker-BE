package com.backgu.amaker.notification.realtime.infra

import com.backgu.amaker.common.http.CaughtHttpExchange
import com.backgu.amaker.notification.realtime.infra.dto.IndividualUserRealTimeNotificationRequest
import com.backgu.amaker.notification.realtime.infra.dto.RealTimeNotificationRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.PostExchange

@CaughtHttpExchange
interface RealTimeClient {
    @PostExchange("/system/v1/users")
    fun publishEvent(
        @RequestBody realTimeNotification: IndividualUserRealTimeNotificationRequest,
    ): List<String>

    @PostExchange("/system/v1/workspaces/{workspace-id}")
    fun publishEventToWorkspace(
        @PathVariable("workspace-id") workspaceId: Long,
        @RequestBody realTimeNotification: RealTimeNotificationRequest,
    ): List<String>
}
