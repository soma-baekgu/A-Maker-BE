package com.backgu.amaker.infra.mail.event

import com.backgu.amaker.infra.mail.constants.EmailConstants

interface EmailEvent {
    val email: String
    val emailConstants: EmailConstants

    fun emailModel(): Map<String, Any>

    fun title(): String
}
