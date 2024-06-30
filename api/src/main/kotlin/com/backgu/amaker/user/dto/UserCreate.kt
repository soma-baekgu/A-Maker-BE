package com.backgu.amaker.user.dto

import com.backgu.amaker.user.domain.User

data class UserCreate(
    var name: String,
    var email: String,
    var picture: String,
) {
    fun toDomain(): User =
        User(
            name = name,
            email = email,
            picture = picture,
        )
}
