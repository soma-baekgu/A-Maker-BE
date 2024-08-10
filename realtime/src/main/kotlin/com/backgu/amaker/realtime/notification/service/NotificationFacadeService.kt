package com.backgu.amaker.realtime.notification.service

import com.backgu.amaker.realtime.notification.dto.RealTimeNotificationDto
import com.backgu.amaker.realtime.session.service.SessionMessageService
import com.backgu.amaker.realtime.user.service.UserSessionService
import com.backgu.amaker.realtime.workspace.service.WorkspaceSessionService
import org.springframework.stereotype.Service

@Service
class NotificationFacadeService(
    private val userSessionService: UserSessionService,
    private val workspaceSessionService: WorkspaceSessionService,
    private val sessionMessageService: SessionMessageService,
) {
    fun sendUserNotification(
        userIds: List<String>,
        realTimeNotificationDto: RealTimeNotificationDto,
    ): Set<String> {
        val sessionMap = userSessionService.getUserSessionToMap(userIds)

        return sessionMap
            .mapNotNull { (userId, sessions) ->
                val (successfulSessions, failedSessions) =
                    sessions.partition { sessionMessageService.sendMessageToSession(it, realTimeNotificationDto) }

                failedSessions.forEach { session ->
                    userSessionService.dropOut(userId, session)
                }

                if (successfulSessions.isEmpty()) null else userId
            }.toSet()
    }

    fun sendWorkspaceNotification(
        workspaceId: Long,
        realTimeNotificationDto: RealTimeNotificationDto,
    ): Set<String> {
        val sessions = workspaceSessionService.getWorkspaceSession(workspaceId)

        val (successfulSessions, failedSessions) =
            sessions.partition { sessionMessageService.sendMessageToSession(it, realTimeNotificationDto) }

        failedSessions.forEach { workspaceSessionService.dropOut(workspaceId, it) }

        return successfulSessions.map { it.userId }.toSet()
    }
}
