package com.backgu.amaker.realtime.notification.controller

import com.backgu.amaker.realtime.notification.dto.request.IndividualUserRealTimeNotificationRequest
import com.backgu.amaker.realtime.notification.dto.request.RealTimeNotificationRequest
import com.backgu.amaker.realtime.notification.service.NotificationFacadeService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/system/v1")
class NotificationController(
    private val notificationFacadeService: NotificationFacadeService,
) {
    @PostMapping("/users")
    fun publishEvent(
        @RequestBody realTimeNotification: IndividualUserRealTimeNotificationRequest,
    ): Set<String> =
        notificationFacadeService.sendUserNotification(
            realTimeNotification.userIds,
            realTimeNotification.toDto(),
        )

    @PostMapping("/workspaces/{workspace-id}")
    fun publishEventToWorkspace(
        @PathVariable("workspace-id") workspaceId: Long,
        @RequestBody realTimeNotification: RealTimeNotificationRequest,
    ): Set<String> = notificationFacadeService.sendWorkspaceNotification(workspaceId, realTimeNotification.toDto())
}
