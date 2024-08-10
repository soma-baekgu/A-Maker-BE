package com.backgu.amaker.notification.service.adapter

import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.domain.notifiacation.WorkspaceNotification
import com.backgu.amaker.domain.notifiacation.method.NotificationMethod
import com.backgu.amaker.domain.notifiacation.method.RealTimeNotificationMethod
import com.backgu.amaker.domain.realtime.RealTimeServer
import com.backgu.amaker.domain.session.Session
import com.backgu.amaker.notification.realtime.handler.RealTimeHandler
import com.backgu.amaker.notification.realtime.service.RealTimeService
import com.backgu.amaker.notification.workspace.service.WorkspaceSessionService
import org.springframework.stereotype.Component

@Component
class RealTimeWorkspaceHandlerAdapter(
    private val realTimeHandler: RealTimeHandler,
    private val realTimeService: RealTimeService,
    private val workspaceSessionService: WorkspaceSessionService,
) : NotificationHandlerAdapter<WorkspaceNotification, RealTimeNotificationMethod> {
    override fun supportsNotification(notification: Notification): Boolean = notification is WorkspaceNotification

    override fun supportsMethod(method: NotificationMethod): Boolean = method is RealTimeNotificationMethod

    override fun process(
        notification: WorkspaceNotification,
        method: RealTimeNotificationMethod,
    ) {
        val sessions: List<Session> = workspaceSessionService.findByWorkspaceId(notification.workspaceId)
        val realTimeServerSet: Set<RealTimeServer> =
            realTimeService.findByIdsToSet(sessions.mapTo(mutableSetOf()) { it.realtimeId })
        realTimeServerSet.map {
            realTimeHandler.handlerWorkspaceRealTimeNotification(notification.workspaceId, it, notification)
        }
    }
}
