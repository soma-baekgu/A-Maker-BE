package com.backgu.amaker.user.dto

import com.backgu.amaker.user.domain.UserRole
import com.backgu.amaker.user.jpa.UserEntity
import java.util.UUID

class UserDto(
    val id: UUID,
    val name: String,
    val email: String,
    val picture: String,
    val userRole: UserRole,
) {
    companion object {
        fun of(userEntity: UserEntity): UserDto =
            UserDto(
                id = userEntity.id,
                name = userEntity.name,
                email = userEntity.email,
                picture = userEntity.picture,
                userRole = userEntity.userRole,
            )
    }
}
