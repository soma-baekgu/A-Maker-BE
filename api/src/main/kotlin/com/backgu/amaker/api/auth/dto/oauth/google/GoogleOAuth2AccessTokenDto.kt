package com.backgu.amaker.api.auth.dto.oauth.google

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GoogleOAuth2AccessTokenDto(
    val accessToken: String,
    val expiresIn: Int,
    val idToken: String,
    val scope: String,
    val tokenType: String,
) {
    fun getBearerToken(): String = "Bearer $accessToken"
}
