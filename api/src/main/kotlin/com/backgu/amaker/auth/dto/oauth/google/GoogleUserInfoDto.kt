package com.backgu.amaker.auth.dto.oauth.google

import com.backgu.amaker.user.domain.User
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GoogleUserInfoDto(
    val id: String,
    val email: String,
    val verifiedEmail: Boolean,
    val name: String,
    val givenName: String,
    val picture: String,
) {
    fun toDomain(): User =
        User(
            name = name,
            email = email,
            picture = picture,
        )
}
