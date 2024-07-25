package com.backgu.amaker.mail.event

import com.backgu.amaker.mail.constants.EmailConstants

interface EmailEvent {
    val email: String
    val emailConstants: EmailConstants

    fun emailModel(): Map<String, Any>

    fun title(): String
}
