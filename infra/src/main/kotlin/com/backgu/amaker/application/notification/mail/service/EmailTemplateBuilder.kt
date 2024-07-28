package com.backgu.amaker.application.notification.mail.service

interface EmailTemplateBuilder {
    fun buildEmailContent(
        templateName: String,
        model: Map<String, Any>,
    ): String
}
