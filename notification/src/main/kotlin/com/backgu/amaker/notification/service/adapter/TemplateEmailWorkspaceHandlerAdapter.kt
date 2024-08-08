package com.backgu.amaker.notification.service.adapter

import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.domain.notifiacation.WorkspaceNotification
import com.backgu.amaker.domain.notifiacation.method.NotificationMethod
import com.backgu.amaker.domain.notifiacation.method.TemplateEmailNotificationMethod
import com.backgu.amaker.notification.email.templated.TemplateEmailHandler
import org.springframework.stereotype.Component

@Component
class TemplateEmailWorkspaceHandlerAdapter(
    private val userService: UserService,
    private val templateEmailHandler: TemplateEmailHandler,
) : NotificationHandlerAdapter<WorkspaceNotification, TemplateEmailNotificationMethod> {
    override fun supportsNotification(notification: Notification): Boolean = notification is WorkspaceNotification

    override fun supportsMethod(method: NotificationMethod): Boolean = method is TemplateEmailNotificationMethod

    override fun process(
        notification: WorkspaceNotification,
        method: TemplateEmailNotificationMethod,
    ) {
        val users = userService.getByWorkspaceId(notification.workspaceId)
        users.forEach { templateEmailHandler.handleEmailEvent(it, method) }
    }
}
