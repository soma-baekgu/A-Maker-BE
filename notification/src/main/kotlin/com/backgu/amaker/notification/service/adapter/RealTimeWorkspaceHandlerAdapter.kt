package com.backgu.amaker.notification.service.adapter

import com.backgu.amaker.application.user.service.UserDeviceService
import com.backgu.amaker.application.workspace.WorkspaceUserService
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.domain.notifiacation.WorkspaceNotification
import com.backgu.amaker.domain.notifiacation.method.NotificationMethod
import com.backgu.amaker.domain.notifiacation.method.RealTimeNotificationMethod
import com.backgu.amaker.domain.realtime.RealTimeServer
import com.backgu.amaker.domain.session.Session
import com.backgu.amaker.notification.realtime.handler.RealTimeHandler
import com.backgu.amaker.notification.realtime.service.RealTimeService
import com.backgu.amaker.notification.workspace.service.WorkspaceSessionService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class RealTimeWorkspaceHandlerAdapter(
    private val realTimeHandler: RealTimeHandler,
    private val realTimeService: RealTimeService,
    private val workspaceSessionService: WorkspaceSessionService,
    private val workspaceUserService: WorkspaceUserService,
    private val userDeviceService: UserDeviceService,
    private val applicationEventPublisher: ApplicationEventPublisher,
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

        val userIds = workspaceUserService.findUserIdsByWorkspaceId(notification.workspaceId)
        val successUsers: List<String> =
            realTimeServerSet
                .map {
                    realTimeHandler.handlerWorkspaceRealTimeNotification(notification.workspaceId, it, notification)
                }.flatten()

        val failedUsers: List<String> = userIds.filterNot { it in successUsers }

        val pushNotification = notification.toPushNotification(userDeviceService.findByUserIds(failedUsers))
        applicationEventPublisher.publishEvent(pushNotification)
    }
}
