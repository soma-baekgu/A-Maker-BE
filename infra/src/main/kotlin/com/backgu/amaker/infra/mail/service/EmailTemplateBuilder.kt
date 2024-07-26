package com.backgu.amaker.infra.mail.service

interface EmailTemplateBuilder {
    fun buildEmailContent(
        templateName: String,
        model: Map<String, Any>,
    ): String
}
