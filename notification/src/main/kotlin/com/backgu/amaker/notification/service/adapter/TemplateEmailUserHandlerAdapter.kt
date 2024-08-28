package com.backgu.amaker.notification.service.adapter

import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.domain.notifiacation.UserNotification
import com.backgu.amaker.domain.notifiacation.method.NotificationMethod
import com.backgu.amaker.domain.notifiacation.method.TemplateEmailNotificationMethod
import com.backgu.amaker.notification.email.service.TemplateEmailHandler
import org.springframework.stereotype.Component

@Component
class TemplateEmailUserHandlerAdapter(
    private val userService: UserService,
    private val templateEmailHandler: TemplateEmailHandler,
) : NotificationHandlerAdapter<UserNotification, TemplateEmailNotificationMethod> {
    override fun supportsNotification(notification: Notification): Boolean = notification is UserNotification

    override fun supportsMethod(method: NotificationMethod): Boolean = method is TemplateEmailNotificationMethod

    override fun process(
        notification: UserNotification,
        method: TemplateEmailNotificationMethod,
    ) {
        val user = userService.getById(notification.userId)
        templateEmailHandler.handleEmailEvent(user, method)
    }
}
