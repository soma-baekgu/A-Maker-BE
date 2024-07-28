package com.backgu.amaker.infra.mail.service

import com.backgu.amaker.application.notification.mail.event.EmailEvent
import com.backgu.amaker.application.notification.mail.service.EmailSender
import com.backgu.amaker.application.notification.mail.service.EmailTemplateBuilder
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class EmailHandler(
    private val emailSender: EmailSender,
    private val emailTemplateBuilder: EmailTemplateBuilder,
) {
    fun handleEmailEvent(event: EmailEvent) {
        val emailMap =
            mapOf(
                "email" to event.email,
                "title" to event.title,
                "content" to event.content,
            )

        emailSender.sendEmail(
            event.email,
            event.title,
            emailTemplateBuilder.buildEmailContent(event.emailConstants.templateName, emailMap),
        )
    }
}
