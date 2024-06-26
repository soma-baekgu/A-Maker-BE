package com.backgu.amaker.auth.dto.oauth.google

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class GoogleUserInfoDto(
    val id: String?,
    val email: String?,
    val verifiedEmail: Boolean?,
    val name: String?,
    val givenName: String?,
    val picture: String?,
)
