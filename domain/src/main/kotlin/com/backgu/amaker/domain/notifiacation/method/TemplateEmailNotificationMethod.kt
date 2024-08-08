package com.backgu.amaker.domain.notifiacation.method

data class TemplateEmailNotificationMethod(
    override val title: String,
    override val message: String,
    val detail: String,
    val templateName: String,
) : RealTimeNotificationMethod()
