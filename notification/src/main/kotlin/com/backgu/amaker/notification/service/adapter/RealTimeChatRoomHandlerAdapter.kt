package com.backgu.amaker.notification.service.adapter

import com.backgu.amaker.application.chat.service.ChatRoomUserService
import com.backgu.amaker.application.user.service.UserDeviceService
import com.backgu.amaker.application.workspace.WorkspaceService
import com.backgu.amaker.domain.notifiacation.ChatRoomNotification
import com.backgu.amaker.domain.notifiacation.Notification
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
class RealTimeChatRoomHandlerAdapter(
    private val realTimeHandler: RealTimeHandler,
    private val realTimeService: RealTimeService,
    private val workspaceSessionService: WorkspaceSessionService,
    private val workspaceService: WorkspaceService,
    private val chatRoomUserService: ChatRoomUserService,
    private val userDeviceService: UserDeviceService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : NotificationHandlerAdapter<ChatRoomNotification, RealTimeNotificationMethod> {
    override fun supportsNotification(notification: Notification): Boolean = notification is ChatRoomNotification

    override fun supportsMethod(method: NotificationMethod): Boolean = method is RealTimeNotificationMethod

    override fun process(
        notification: ChatRoomNotification,
        method: RealTimeNotificationMethod,
    ) {
        val workspaceId: Long = workspaceService.getWorkspaceIdByChatRoomId(notification.chatRoomId)
        val sessions: List<Session> = workspaceSessionService.findByWorkspaceId(workspaceId)
        val realTimeServerSet: Set<RealTimeServer> =
            realTimeService.findByIdsToSet(sessions.mapTo(mutableSetOf()) { it.realtimeId })

        val chatRoomUsers = chatRoomUserService.findUserIdsByChatRoomId(notification.chatRoomId)
        val successUsers: List<String> =
            realTimeServerSet
                .map {
                    realTimeHandler.handleUserRealTimeNotification(chatRoomUsers, it, notification)
                }.flatten()

        val failedUsers: List<String> = chatRoomUsers.filterNot { it in successUsers }

        val pushNotification = notification.toPushNotification(userDeviceService.findByUserIds(failedUsers))
        applicationEventPublisher.publishEvent(pushNotification)
    }
}
