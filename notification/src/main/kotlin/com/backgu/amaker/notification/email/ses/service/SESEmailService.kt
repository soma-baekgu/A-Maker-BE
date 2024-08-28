package com.backgu.amaker.notification.email.ses.service

import com.backgu.amaker.notification.email.service.EmailSender
import com.backgu.amaker.notification.email.ses.config.AWSSESConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import software.amazon.awssdk.services.ses.SesClient
import software.amazon.awssdk.services.ses.model.Body
import software.amazon.awssdk.services.ses.model.Content
import software.amazon.awssdk.services.ses.model.Destination
import software.amazon.awssdk.services.ses.model.Message
import software.amazon.awssdk.services.ses.model.SendEmailRequest
import software.amazon.awssdk.services.ses.model.SesException

private val logger = KotlinLogging.logger {}

class SESEmailService(
    private val sesClient: SesClient,
    private val sesConfig: AWSSESConfig,
) : EmailSender {
    override fun sendEmail(
        emailAddress: String,
        title: String,
        body: String,
    ) {
        val destination: Destination =
            Destination
                .builder()
                .toAddresses(emailAddress)
                .build()

        val subjectContent: Content =
            Content
                .builder()
                .data(title)
                .charset("UTF-8")
                .build()

        val bodyContent: Content =
            Content
                .builder()
                .data(body)
                .charset("UTF-8")
                .build()

        val body: Body =
            Body
                .builder()
                .html(bodyContent)
                .build()

        val message: Message =
            Message
                .builder()
                .subject(subjectContent)
                .body(body)
                .build()

        val request: SendEmailRequest =
            SendEmailRequest
                .builder()
                .destination(destination)
                .message(message)
                .source(sesConfig.sender)
                .build()

        try {
            sesClient.sendEmail(request)
        } catch (e: SesException) {
            logger.error(e) { "Failed to send email to $emailAddress with title $title and body $body" }
            throw e
        }
    }
}
