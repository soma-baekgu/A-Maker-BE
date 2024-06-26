package com.backgu.amaker.fixture

import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.dto.UserCreateDto
import java.util.UUID

class UserFixture {
    companion object {
        val defaultUserId = UUID.fromString("00000000-0000-0000-0000-000000000000")

        fun createUserRequest() =
            UserCreateDto(
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
}
