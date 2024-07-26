package com.backgu.amaker.api.auth.dto

import com.backgu.amaker.domain.user.User

data class JwtTokenDto(
    val token: String,
    val name: String,
    val email: String,
    val picture: String,
) {
    companion object {
        fun of(
            token: String,
            user: User,
        ): JwtTokenDto = JwtTokenDto(token, user.name, user.email, user.picture)
    }
}
