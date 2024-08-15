package com.backgu.amaker.infra.mail.infra

import com.backgu.amaker.application.notification.mail.service.EmailSender
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class HtmlEmailSender(
    val mailSender: JavaMailSender,
) : EmailSender {
    override fun sendEmail(
        emailAddress: String,
        title: String,
        body: String,
    ) {
        try {
            val msg: MimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(msg, true, "UTF-8")

            helper.setTo(emailAddress)
            helper.setSubject(title)
            helper.setText(body, true)

            mailSender.send(msg)
        } catch (e: Exception) {
            logger.error(e) { "Failed to send email to $emailAddress with title $title and body $body" }
            throw RuntimeException("Failed to send email to $emailAddress with title $title and body $body")
        }
    }
}
