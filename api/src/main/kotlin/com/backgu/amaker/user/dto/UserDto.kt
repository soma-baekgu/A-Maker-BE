package com.backgu.amaker.user.dto

import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.domain.UserRole
import java.util.UUID

class UserDto(
    val id: UUID,
    val name: String,
    val email: String,
    val picture: String,
    val userRole: UserRole,
) {
    companion object {
        fun of(user: User): UserDto =
            UserDto(
                id = user.id,
                name = user.name,
                email = user.email,
                picture = user.picture,
                userRole = user.userRole,
            )
    }
}
