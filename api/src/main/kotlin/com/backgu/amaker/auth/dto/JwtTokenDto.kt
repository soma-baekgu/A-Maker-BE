package com.backgu.amaker.auth.dto

import com.backgu.amaker.user.domain.User

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
