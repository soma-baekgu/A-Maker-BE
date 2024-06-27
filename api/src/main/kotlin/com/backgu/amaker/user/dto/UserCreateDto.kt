package com.backgu.amaker.user.dto

import com.backgu.amaker.user.domain.User

data class UserCreateDto(
    var name: String,
    var email: String,
    var picture: String,
) {
    fun toEntity(): User =
        User(
            name = name,
            email = email,
            picture = picture,
        )
}
