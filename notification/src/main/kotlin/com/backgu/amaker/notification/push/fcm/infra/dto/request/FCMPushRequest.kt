package com.backgu.amaker.notification.push.fcm.infra.dto.request

data class FCMPushRequest(
    val validateOnly: Boolean? = null,
    val message: FCMMessage,
)
