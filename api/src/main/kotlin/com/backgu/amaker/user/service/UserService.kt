package com.backgu.amaker.user.service

import com.backgu.amaker.user.dto.UserCreateDto
import com.backgu.amaker.user.dto.UserDto
import com.backgu.amaker.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    val userRepository: UserRepository,
) {
    @Transactional
    fun saveUser(create: UserCreateDto): UserDto =
        UserDto.of(
            userRepository.save(create.toEntity()),
        )

    fun saveOrGetUser(user: UserCreateDto): UserDto =
        userRepository.findByEmail(user.email)?.let { UserDto.of(it) }
            ?: saveUser(user)

    fun getByEmail(email: String): UserDto =
        UserDto.of(userRepository.findByEmail(email) ?: throw IllegalArgumentException("User not found"))
}
