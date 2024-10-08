package com.backgu.amaker.api.auth.test

import com.backgu.amaker.api.auth.dto.oauth.google.GoogleOAuth2AccessTokenDto
import com.backgu.amaker.api.auth.infra.GoogleOAuthClient

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
