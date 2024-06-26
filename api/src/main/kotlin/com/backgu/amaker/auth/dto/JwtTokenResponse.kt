package com.backgu.amaker.auth.dto

import com.backgu.amaker.user.dto.UserDto

class JwtTokenResponse(
    val token: String,
    user: UserDto,
) {
    val name: String = user.name
    val email: String = user.email
    val picture: String = user.picture
}
