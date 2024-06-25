package com.backgu.amaker.auth.service

import com.backgu.amaker.auth.dto.GoogleOAuth2AccessTokenDto
import com.backgu.amaker.auth.infra.GoogleOAuthClient

class SuccessfulStubGoogleOAuthClient : GoogleOAuthClient {
    private val accessTokenDto =
        GoogleOAuth2AccessTokenDto(
            accessToken = "stubAccessToken",
            expiresIn = 3600,
            idToken = "mockRefreshToken",
            scope = "email",
            tokenType = "Bearer",
        )

    override fun getGoogleOAuth2(
        authCode: String,
        redirectUri: String,
        grantType: String,
        clientSecret: String,
        clientId: String,
    ): GoogleOAuth2AccessTokenDto = accessTokenDto
}
