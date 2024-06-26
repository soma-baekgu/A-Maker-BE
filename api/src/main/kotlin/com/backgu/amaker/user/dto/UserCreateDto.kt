package com.backgu.amaker.user.dto

import com.backgu.amaker.user.domain.User

class UserCreateDto(
    name: String?,
    email: String?,
    picture: String?,
) {
    var name: String = name ?: ""
    var email: String = email ?: ""
    var picture: String = picture ?: ""

    fun toEntity(): User =
        User(
            name = name,
            email = email,
            picture = picture,
        )
}
