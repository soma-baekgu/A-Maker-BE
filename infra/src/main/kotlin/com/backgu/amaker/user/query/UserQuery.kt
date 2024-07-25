package com.backgu.amaker.user.query

import com.backgu.amaker.user.domain.User

class UserQuery(
    val id: String,
    val name: String,
    val email: String,
    val picture: String,
) {
    companion object {
        fun of(user: User): UserQuery =
            UserQuery(
                id = user.id,
                name = user.name,
                email = user.email,
                picture = user.picture,
            )
    }
}
