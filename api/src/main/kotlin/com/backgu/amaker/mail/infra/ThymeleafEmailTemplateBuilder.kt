package com.backgu.amaker.mail.infra

import com.backgu.amaker.mail.service.EmailTemplateBuilder
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Component
class ThymeleafEmailTemplateBuilder(
    private val templateEngine: TemplateEngine,
) : EmailTemplateBuilder {
    override fun buildEmailContent(
        templateName: String,
        model: Map<String, Any>,
    ): String {
        val context = Context()
        context.setVariables(model)
        return templateEngine.process(templateName, context)
    }
}
