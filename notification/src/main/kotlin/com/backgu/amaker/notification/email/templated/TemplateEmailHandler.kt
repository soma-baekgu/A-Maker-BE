package com.backgu.amaker.notification.email.templated

import com.backgu.amaker.application.notification.mail.service.EmailSender
import com.backgu.amaker.application.notification.mail.service.EmailTemplateBuilder
import com.backgu.amaker.domain.notifiacation.method.TemplateEmailNotificationMethod
import com.backgu.amaker.domain.user.User
import org.springframework.stereotype.Service

@Service
class TemplateEmailHandler(
    private val emailSender: EmailSender,
    private val emailTemplateBuilder: EmailTemplateBuilder,
) {
    fun handleEmailEvent(
        user: User,
        email: TemplateEmailNotificationMethod,
    ) {
        val emailMap =
            mapOf(
                "email" to user.email,
                "title" to email.title,
                "content" to email.message,
            )

        emailSender.sendEmail(
            user.email,
            email.title,
            emailTemplateBuilder.buildEmailContent(email.templateName, emailMap),
        )
    }
}
