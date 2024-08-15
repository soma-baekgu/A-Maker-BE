package com.backgu.amaker.infra.jpa.user.query

import com.backgu.amaker.domain.user.User

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
