package com.backgu.amaker.mail.service

import com.backgu.amaker.mail.event.EmailEvent
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val emailSender: EmailSender,
    private val emailTemplateBuilder: EmailTemplateBuilder,
) {
    fun sendEmailEvent(emailEvent: EmailEvent) {
        emailSender.sendEmail(
            emailEvent.email,
            emailEvent.title(),
            emailTemplateBuilder.buildEmailContent(emailEvent.emailConstants.templateName, emailEvent.emailModel()),
        )
    }
}
