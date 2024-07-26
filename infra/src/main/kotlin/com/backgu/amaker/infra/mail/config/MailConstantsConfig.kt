package com.backgu.amaker.infra.mail.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.Properties

@Configuration
class MailConstantsConfig(
    private val mailClientConfig: MailClientConfig,
) {
    @Bean
    fun javaMailSender(): JavaMailSender {
        val javaMailSender = JavaMailSenderImpl()

        javaMailSender.host = mailClientConfig.host
        javaMailSender.port = mailClientConfig.port.toInt()
        javaMailSender.username = mailClientConfig.username
        javaMailSender.password = mailClientConfig.password

        val props: Properties = javaMailSender.javaMailProperties
        props["mail.smtp.starttls.enable"] = mailClientConfig.starttls.toBoolean()
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = mailClientConfig.auth.toBoolean()
        props["mail.smtp.starttls.enable"] = mailClientConfig.starttls.toBoolean()

        return javaMailSender
    }
}
