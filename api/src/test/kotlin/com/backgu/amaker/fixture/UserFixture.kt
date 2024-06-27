package com.backgu.amaker.fixture

import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.dto.UserRequest
import com.backgu.amaker.user.repository.UserRepository
import java.util.UUID

class UserFixture(
    private val userRepository: UserRepository,
) {
    companion object {
        val defaultUserId = UUID.fromString("00000000-0000-0000-0000-000000000001")

        fun createUserRequest() =
            UserRequest(
                name = "name",
                email = "email",
                picture = "picture",
            )

        fun createUser(userId: UUID) =
            User(
                id = userId,
                name = "name",
                email = "email",
                picture = "picture",
            )
    }

    fun testUserSetUp() {
        userRepository.save(createUser(defaultUserId))
        for (i in 2..9) {
            userRepository.save(createUser(UUID.fromString("00000000-0000-0000-0000-00000000000$i")))
        }
    }
}
