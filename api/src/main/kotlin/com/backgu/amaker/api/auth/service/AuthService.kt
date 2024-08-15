package com.backgu.amaker.api.auth.service

import com.backgu.amaker.api.auth.config.AuthConfig
import com.backgu.amaker.api.auth.dto.oauth.google.GoogleOAuth2AccessTokenDto
import com.backgu.amaker.api.auth.dto.oauth.google.GoogleUserInfoDto
import com.backgu.amaker.api.auth.infra.GoogleApiClient
import com.backgu.amaker.api.auth.infra.GoogleOAuthClient
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val googleOAuthClient: GoogleOAuthClient,
    private val googleApiClient: GoogleApiClient,
    private val authConfig: AuthConfig,
) {
    fun googleLogin(authorizationCode: String): GoogleUserInfoDto {
        val accessTokenDto: GoogleOAuth2AccessTokenDto =
            googleOAuthClient.getGoogleOAuth2(
                authorizationCode,
                authConfig.redirectUri,
                authConfig.grantType,
                authConfig.clientSecret,
                authConfig.clientId,
            ) ?: throw BusinessException(StatusCode.OAUTH_SOCIAL_LOGIN_FAILED)

        val userInfo: GoogleUserInfoDto =
            googleApiClient.getUserInfo(accessTokenDto.getBearerToken())
                ?: throw BusinessException(StatusCode.OAUTH_SOCIAL_LOGIN_FAILED)

        return userInfo
    }
}
