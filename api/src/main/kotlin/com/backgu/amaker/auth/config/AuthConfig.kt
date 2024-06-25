package com.backgu.amaker.auth.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class AuthConfig {
    @Value("\${oauth.google.client-id}")
    lateinit var clientId: String

    @Value("\${oauth.google.client-secret}")
    lateinit var clientSecret: String

    @Value("\${oauth.google.redirect-uri}")
    lateinit var redirectUri: String

    @Value("\${oauth.google.client-name}")
    lateinit var clientName: String

    @Value("\${oauth.google.base-url}")
    lateinit var baseUrl: String

    @Value("\${oauth.google.scope}")
    lateinit var scope: String

    @Value("\${oauth.google.oauth-url}")
    lateinit var oauthUrl: String

    @Value("\${oauth.google.api-url}")
    lateinit var apiUrl: String

    var grantType = "authorization_code"

    fun oauthUrl(): String =
        baseUrl +
            "?client_id=$clientId" +
            "&redirect_uri=${java.net.URLEncoder.encode(redirectUri, "UTF-8")}" +
            "&response_type=code" +
            "&scope=${scope.replace(",", "%20")}"
}
