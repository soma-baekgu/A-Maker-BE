package com.backgu.amaker.notification.email.service

interface EmailSender {
    fun sendEmail(
        emailAddress: String,
        title: String,
        body: String,
    )
}
