package com.backgu.amaker.notification.push.fcm.infra.dto.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class FCMNotification(
    val title: String,
    val body: String,
    val image: String? = null,
)
