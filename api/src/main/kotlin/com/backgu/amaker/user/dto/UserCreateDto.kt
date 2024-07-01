package com.backgu.amaker.user.dto

import com.backgu.amaker.user.jpa.UserEntity

data class UserCreateDto(
    var name: String,
    var email: String,
    var picture: String,
) {
    fun toEntity(): UserEntity =
        UserEntity(
            name = name,
            email = email,
            picture = picture,
        )
}
