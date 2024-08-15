package com.backgu.amaker.notification.service.adapter

import com.backgu.amaker.application.user.service.UserDeviceService
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.domain.notifiacation.UserNotification
import com.backgu.amaker.domain.notifiacation.method.NotificationMethod
import com.backgu.amaker.domain.notifiacation.method.RealTimeNotificationMethod
import com.backgu.amaker.domain.realtime.RealTimeServer
import com.backgu.amaker.domain.session.Session
import com.backgu.amaker.notification.realtime.handler.RealTimeHandler
import com.backgu.amaker.notification.realtime.service.RealTimeService
import com.backgu.amaker.notification.user.service.UserSessionService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class RealTimeUserHandlerAdapter(
    private val realTimeHandler: RealTimeHandler,
    private val realTimeService: RealTimeService,
    private val userSessionService: UserSessionService,
    private val userDeviceService: UserDeviceService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : NotificationHandlerAdapter<UserNotification, RealTimeNotificationMethod> {
    override fun supportsNotification(notification: Notification): Boolean = notification is UserNotification

    override fun supportsMethod(method: NotificationMethod): Boolean = method is RealTimeNotificationMethod

    override fun process(
        notification: UserNotification,
        method: RealTimeNotificationMethod,
    ) {
        val sessions: List<Session> = userSessionService.findByUserId(notification.userId)
        val realTimeServerSet: Set<RealTimeServer> =
            realTimeService.findByIdsToSet(sessions.mapTo(mutableSetOf()) { it.realtimeId })
        val successUser =
            realTimeServerSet.map {
                realTimeHandler.handleUserRealTimeNotification(notification.userId, it, notification)
            }

        if (successUser.isEmpty()) {
            applicationEventPublisher.publishEvent(
                notification.toPushNotification(userDeviceService.findByUserIds(listOf(notification.userId))),
            )
        }
    }
}
