package com.backgu.amaker.notification.email.service

interface EmailTemplateBuilder {
    fun buildEmailContent(
        templateName: String,
        model: Map<String, Any>,
    ): String
}
