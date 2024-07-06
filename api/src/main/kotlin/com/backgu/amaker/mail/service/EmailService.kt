package com.backgu.amaker.mail.service

import com.backgu.amaker.mail.event.EmailEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener

private val logger = KotlinLogging.logger {}

@Service
class EmailService(
    private val emailSender: EmailSender,
    private val emailTemplateBuilder: EmailTemplateBuilder,
) {
    @Async
    @TransactionalEventListener
    fun handleEmailEvent(event: EmailEvent) {
        logger.info { "email send to ${event.email}" }
        emailSender.sendEmail(
            event.email,
            event.title(),
            emailTemplateBuilder.buildEmailContent(event.emailConstants.templateName, event.emailModel()),
        )
    }
}
