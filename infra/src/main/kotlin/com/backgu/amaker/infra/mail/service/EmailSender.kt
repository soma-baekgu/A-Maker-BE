package com.backgu.amaker.infra.mail.service

interface EmailSender {
    fun sendEmail(
        emailAddress: String,
        title: String,
        body: String,
    )
}
