package com.backgu.amaker.user.service

import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.dto.UserRequest
import com.backgu.amaker.user.repository.UserRepository
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository,
) {
    fun saveUser(request: UserRequest): UUID =
        userRepository
            .save(
                User(
                    name = request.name,
                    email = request.email,
                    picture = request.picture,
                ),
            ).id
}
