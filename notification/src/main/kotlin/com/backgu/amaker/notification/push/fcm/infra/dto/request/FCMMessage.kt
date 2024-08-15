package com.backgu.amaker.notification.push.fcm.infra.dto.request

data class FCMMessage(
    val notification: FCMNotification,
    val token: String,
)
