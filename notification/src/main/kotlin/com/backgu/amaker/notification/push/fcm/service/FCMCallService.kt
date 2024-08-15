package com.backgu.amaker.notification.push.fcm.service

import com.backgu.amaker.notification.push.fcm.config.FCMConfig
import com.backgu.amaker.notification.push.fcm.infra.FcmClient
import com.backgu.amaker.notification.push.fcm.infra.dto.request.FCMMessage
import com.backgu.amaker.notification.push.fcm.infra.dto.request.FCMNotification
import com.backgu.amaker.notification.push.fcm.infra.dto.request.FCMPushRequest
import com.backgu.amaker.notification.push.fcm.infra.dto.response.FCMResponse
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class FCMCallService(
    private val fcmClient: FcmClient,
    private val fcmConfig: FCMConfig,
) {
    companion object {
        fun makeBearerToken(accessToken: String) = "Bearer $accessToken"
    }

    fun sendMessage(
        token: String,
        title: String,
        body: String,
    ): FCMResponse? {
        return try {
            return fcmClient.sendPushNotification(
                makeBearerToken(getAccessToken()),
                makeMessage(
                    token,
                    title,
                    body,
                ),
            )
        } catch (e: Exception) {
            null
        }
    }

    fun makeMessage(
        targetToken: String,
        title: String,
        body: String,
    ): FCMPushRequest =
        FCMPushRequest(
            validateOnly = false,
            message =
                FCMMessage(
                    notification =
                        FCMNotification(
                            title = title,
                            body = body,
                        ),
                    token = targetToken,
                ),
        )

    fun getAccessToken(): String {
        val credentials: GoogleCredentials =
            GoogleCredentials
                .fromStream(
                    ClassPathResource(fcmConfig.file).inputStream,
                ).createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
        credentials.refreshIfExpired()
        return credentials.accessToken.tokenValue
    }
}
