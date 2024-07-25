package com.backgu.amaker.mail.service

interface EmailSender {
    fun sendEmail(
        emailAddress: String,
        title: String,
        body: String,
    )
}
