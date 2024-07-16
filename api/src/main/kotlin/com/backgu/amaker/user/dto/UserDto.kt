package com.backgu.amaker.user.dto

import com.backgu.amaker.user.domain.User

class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val picture: String,
) {
    companion object {
        fun of(user: User): UserDto =
            UserDto(
                id = user.id,
                name = user.name,
                email = user.email,
                picture = user.picture,
            )
    }
}
