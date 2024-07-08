package com.backgu.amaker.mail.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class MailClientConfig {
    @Value("\${spring.mail.host}")
    lateinit var host: String

    @Value("\${spring.mail.port}")
    lateinit var port: String

    @Value("\${spring.mail.username}")
    lateinit var username: String

    @Value("\${spring.mail.password}")
    lateinit var password: String

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    lateinit var auth: String

    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    lateinit var starttls: String
}
