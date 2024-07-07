package com.backgu.amaker.mail.infra

import com.backgu.amaker.mail.service.EmailTemplateBuilder
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

private val logger = KotlinLogging.logger {}

@Component
class ThymeleafEmailTemplateBuilder(
    private val templateEngine: TemplateEngine,
) : EmailTemplateBuilder {
    override fun buildEmailContent(
        templateName: String,
        model: Map<String, Any>,
    ): String {
        try {
            val context = Context()
            context.setVariables(model)
            return templateEngine.process(templateName, context)
        } catch (e: Exception) {
            logger.error(e) { "Failed to Build email of $templateName" }
            throw RuntimeException("Failed to Build email of $templateName")
        }
    }
}
