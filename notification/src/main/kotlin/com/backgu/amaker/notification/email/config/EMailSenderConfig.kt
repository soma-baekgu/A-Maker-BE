package com.backgu.amaker.notification.email.config

import com.backgu.amaker.notification.email.service.EmailSender
import com.backgu.amaker.notification.email.ses.config.AWSSESConfig
import com.backgu.amaker.notification.email.ses.service.SESEmailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.ses.SesClient

@Configuration
class EMailSenderConfig {
    @Bean
    fun mailSender(
        sesClient: SesClient,
        sesConfig: AWSSESConfig,
    ): EmailSender = SESEmailService(sesClient, sesConfig)
}
