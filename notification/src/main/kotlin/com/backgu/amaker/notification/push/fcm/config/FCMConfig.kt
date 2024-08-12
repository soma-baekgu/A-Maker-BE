package com.backgu.amaker.notification.push.fcm.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "fcm")
class FCMConfig {
    companion object {
        const val FCM_PREFIX = "firebase"
    }

    var file: String = ""
        get() = "$FCM_PREFIX/$field"
    lateinit var baseUrl: String
    lateinit var authUrl: String
}
