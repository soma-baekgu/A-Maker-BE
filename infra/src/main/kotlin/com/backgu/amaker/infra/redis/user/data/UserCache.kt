package com.backgu.amaker.infra.redis.user.data

import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.user.UserRole
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("user")
class UserCache(
    @Id
    val id: String,
    val name: String,
    val email: String,
    val picture: String,
    val userRole: UserRole = UserRole.USER,
) {
    fun toDomain(): User =
        User(
            id = id,
            name = name,
            email = email,
            picture = picture,
            userRole = userRole,
        )

    companion object {
        fun of(user: User): UserCache =
            UserCache(
                id = user.id,
                name = user.name,
                email = user.email,
                picture = user.picture,
                userRole = user.userRole,
            )
    }
}
