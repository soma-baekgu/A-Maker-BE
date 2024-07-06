package com.backgu.amaker.mail.infra

import com.backgu.amaker.mail.config.MailClientConfig
import com.backgu.amaker.mail.service.EmailSender
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Component
class HtmlEmailSender(
    val mailSender: JavaMailSender,
    val mailClientConfig: MailClientConfig,
) : EmailSender {
    override fun sendEmail(
        emailAddress: String,
        title: String,
        body: String,
    ) {
        val msg: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(msg, true, "UTF-8")

        helper.setTo(emailAddress)
        helper.setSubject(title)
        helper.setText(body, true)

        mailSender.send(msg)
    }
}
