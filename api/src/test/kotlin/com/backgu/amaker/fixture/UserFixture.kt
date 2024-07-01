package com.backgu.amaker.fixture

import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.jpa.UserEntity
import com.backgu.amaker.user.repository.UserRepository
import java.util.UUID

class UserFixture(
    private val userRepository: UserRepository,
) {
    companion object {
        val defaultUserId = UUID.fromString("00000000-0000-0000-0000-000000000001")

        fun createUser() =
            User(
                name = "name",
                email = "email",
                picture = "picture",
            )

        fun createUserEntity(userId: UUID) =
            UserEntity(
                id = userId,
                name = "name",
                email = "email",
                picture = "picture",
            )
    }

    fun testUserSetUp() {
        userRepository.save(createUserEntity(defaultUserId))
        for (i in 2..9) {
            userRepository.save(createUserEntity(UUID.fromString("00000000-0000-0000-0000-00000000000$i")))
        }
    }
}
