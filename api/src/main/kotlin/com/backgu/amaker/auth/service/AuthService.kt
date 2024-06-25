package com.backgu.amaker.auth.service

import com.backgu.amaker.auth.config.AuthConfig
import com.backgu.amaker.auth.dto.GoogleOAuth2AccessTokenDto
import com.backgu.amaker.auth.dto.GoogleUserInfoDto
import com.backgu.amaker.auth.infra.GoogleApiClient
import com.backgu.amaker.auth.infra.GoogleOAuthClient
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class AuthService(
    val googleOAuthClient: GoogleOAuthClient,
    val googleApiClient: GoogleApiClient,
    val authConfig: AuthConfig,
) {
    fun googleLogin(authorizationCode: String): String? {
        val accessTokenDto: GoogleOAuth2AccessTokenDto =
            googleOAuthClient.getGoogleOAuth2(
                authorizationCode,
                authConfig.redirectUri,
                authConfig.grantType,
                authConfig.clientSecret,
                authConfig.clientId,
            ) ?: throw IllegalArgumentException("Failed to get access token")

        val userInfo: GoogleUserInfoDto =
            googleApiClient.getUserInfo(accessTokenDto.getBearerToken())
                ?: throw IllegalArgumentException("Failed to get user information")

        return userInfo.email
    }
}
