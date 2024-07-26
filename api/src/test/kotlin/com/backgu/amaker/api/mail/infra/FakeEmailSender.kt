package com.backgu.amaker.api.mail.infra

import com.backgu.amaker.api.common.annotation.IntegrationTestComponent
import com.backgu.amaker.infra.mail.service.EmailSender
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
