package com.backgu.amaker.user.service

import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.dto.UserRequest
import com.backgu.amaker.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class UserService(
    val userRepository: UserRepository,
) {
    @Transactional
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
