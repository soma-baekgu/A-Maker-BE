package com.backgu.amaker.notification.push.fcm.infra

import com.backgu.amaker.common.http.CaughtHttpExchange
import com.backgu.amaker.notification.push.fcm.infra.dto.request.FCMPushRequest
import com.backgu.amaker.notification.push.fcm.infra.dto.response.FCMResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.PostExchange

@CaughtHttpExchange
interface FcmClient {
    @PostExchange("/messages:send", contentType = MediaType.APPLICATION_JSON_VALUE)
    fun sendPushNotification(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody fcmRequest: FCMPushRequest,
    ): FCMResponse
}
