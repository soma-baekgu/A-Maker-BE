package com.backgu.amaker.mail.infra

import com.backgu.amaker.common.annotation.IntegrationTestComponent
import com.backgu.amaker.mail.service.EmailSender
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

@IntegrationTestComponent
class FakeEmailSender : EmailSender {
    override fun sendEmail(
        emailAddress: String,
        title: String,
        body: String,
    ) {
        logger.info { "Test Sending email to $emailAddress with title $title and body $body" }
    }
}
