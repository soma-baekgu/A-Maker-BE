package com.backgu.amaker.user.fixture

import com.backgu.amaker.user.dto.UserRequest

class UserFixture {
    companion object {
        fun createUserRequest() =
            UserRequest(
                name = "name",
                email = "email",
                picture = "picture",
            )
    }
}
