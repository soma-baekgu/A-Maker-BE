package com.backgu.amaker.notification.service.adapter

import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.domain.notifiacation.ChatRoomNotification
import com.backgu.amaker.domain.notifiacation.Notification
import com.backgu.amaker.domain.notifiacation.method.NotificationMethod
import com.backgu.amaker.domain.notifiacation.method.TemplateEmailNotificationMethod
import com.backgu.amaker.notification.email.service.TemplateEmailHandler
import org.springframework.stereotype.Component

@Component
class TemplateEmailChatRoomHandlerAdapter(
    private val userService: UserService,
    private val templateEmailHandler: TemplateEmailHandler,
) : NotificationHandlerAdapter<ChatRoomNotification, TemplateEmailNotificationMethod> {
    override fun supportsNotification(notification: Notification): Boolean = notification is ChatRoomNotification

    override fun supportsMethod(method: NotificationMethod): Boolean = method is TemplateEmailNotificationMethod

    override fun process(
        notification: ChatRoomNotification,
        method: TemplateEmailNotificationMethod,
    ) {
        val users = userService.getByChatRoomId(notification.chatRoomId)
        users.forEach { templateEmailHandler.handleEmailEvent(it, method) }
    }
}
