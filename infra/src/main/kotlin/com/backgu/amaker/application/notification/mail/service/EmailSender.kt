package com.backgu.amaker.application.notification.mail.service

interface EmailSender {
    fun sendEmail(
        emailAddress: String,
        title: String,
        body: String,
    )
}
