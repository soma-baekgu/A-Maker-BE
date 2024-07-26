package com.backgu.amaker.api.auth.dto.oauth.google

import com.backgu.amaker.api.common.service.IdPublisher
import com.backgu.amaker.domain.user.User
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
    fun toDomain(idPublisher: IdPublisher): User =
        User(
            id = idPublisher.publishId(),
            name = name,
            email = email,
            picture = picture,
        )
}
