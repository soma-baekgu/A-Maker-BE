package com.backgu.amaker.auth.dto

import com.backgu.amaker.user.domain.User

class JwtTokenResponse(
    val token: String,
    user: User,
) {
    val name: String = user.name
    val email: String = user.email
    val picture: String = user.picture
}
