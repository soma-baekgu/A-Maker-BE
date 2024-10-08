package com.backgu.amaker.notification.email.gmail.infra

import com.backgu.amaker.notification.email.service.EmailSender
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper

private val logger = KotlinLogging.logger {}

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
