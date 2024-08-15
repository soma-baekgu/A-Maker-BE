package com.backgu.amaker.notification.service.adapter

import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.domain.notifiacation.UserFulfilledNotification
import com.backgu.amaker.domain.notifiacation.method.NotificationMethod
import com.backgu.amaker.domain.notifiacation.method.TemplateEmailNotificationMethod
import com.backgu.amaker.notification.email.templated.TemplateEmailHandler
import org.springframework.stereotype.Component

@Component
class TemplateEmailUserFulfilledHandlerAdapter(
    private val templateEmailHandler: TemplateEmailHandler,
) : NotificationHandlerAdapter<UserFulfilledNotification, TemplateEmailNotificationMethod> {
    override fun supportsNotification(notification: Notification): Boolean = notification is UserFulfilledNotification

    override fun supportsMethod(method: NotificationMethod): Boolean = method is TemplateEmailNotificationMethod

    override fun process(
        notification: UserFulfilledNotification,
        method: TemplateEmailNotificationMethod,
    ) {
        templateEmailHandler.handleEmailEvent(notification.user, method)
    }
}
