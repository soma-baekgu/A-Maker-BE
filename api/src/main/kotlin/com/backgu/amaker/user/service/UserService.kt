package com.backgu.amaker.user.service

import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.jpa.UserEntity
import com.backgu.amaker.user.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun save(user: User): User {
        val savedUser: UserEntity = userRepository.save(UserEntity.of(user))
        return savedUser.toDomain()
    }

    // TODO exception handling
    fun getById(id: String): User =
        userRepository.findByIdOrNull(id)?.toDomain() ?: run {
            logger.error { "User not found : $id" }
            throw IllegalArgumentException("User not found : $id")
        }

    fun getByEmail(email: String): User {
        // TODO exception handling
        return userRepository.findByEmail(email)?.toDomain() ?: throw IllegalArgumentException("User not found")
    }

    @Transactional
    fun saveOrGetUser(user: User): User = userRepository.findByEmail(user.email)?.toDomain() ?: save(user)
}
