package com.backgu.amaker.auth.dto

import com.backgu.amaker.user.domain.User

class JwtTokenResponse(
    val token: String,
    val name: String,
    val email: String,
    val picture: String,
) {
    companion object {
        fun of(
            token: String,
            user: User,
        ): JwtTokenResponse = JwtTokenResponse(token, user.name, user.email, user.picture)
    }
}
