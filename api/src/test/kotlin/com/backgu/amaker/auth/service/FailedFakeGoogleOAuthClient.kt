package com.backgu.amaker.auth.service

import com.backgu.amaker.auth.dto.GoogleOAuth2AccessTokenDto
import com.backgu.amaker.auth.infra.GoogleOAuthClient

class FailedFakeGoogleOAuthClient : GoogleOAuthClient {
    override fun getGoogleOAuth2(
        authCode: String,
        redirectUri: String,
        grantType: String,
        clientSecret: String,
        clientId: String,
    ): GoogleOAuth2AccessTokenDto? {
        throw IllegalArgumentException("Failed to get access token")
    }
}
