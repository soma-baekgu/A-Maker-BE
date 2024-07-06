package com.backgu.amaker.mail.service

interface EmailTemplateBuilder {
    fun buildEmailContent(
        templateName: String,
        model: Map<String, Any>,
    ): String
}
