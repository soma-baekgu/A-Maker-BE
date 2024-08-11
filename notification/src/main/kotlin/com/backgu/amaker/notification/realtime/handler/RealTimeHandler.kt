package com.backgu.amaker.notification.realtime.handler

import com.backgu.amaker.domain.notifiacation.RealTimeBasedNotification
import com.backgu.amaker.domain.realtime.RealTimeServer
import com.backgu.amaker.notification.realtime.service.RealTimeCallService
import org.springframework.stereotype.Service

@Service
class RealTimeHandler(
    private val realTimeCallService: RealTimeCallService,
) {
    fun handleUserRealTimeNotification(
        userId: String,
        realTimeServer: RealTimeServer,
        event: RealTimeBasedNotification,
    ): List<String> = realTimeCallService.sendUserRealTimeNotification(listOf(userId), realTimeServer, event)

    fun handlerWorkspaceRealTimeNotification(
        workspaceId: Long,
        realTimeServer: RealTimeServer,
        event: RealTimeBasedNotification,
    ): List<String> = realTimeCallService.sendWorkspaceRealTimeNotification(workspaceId, realTimeServer, event)
}
