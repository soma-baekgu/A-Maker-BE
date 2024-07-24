package com.backgu.amaker.user.service

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.exception.StatusCode
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

    fun getById(id: String): User =
        userRepository.findByIdOrNull(id)?.toDomain() ?: run {
            // TODO 로깅 전략 세우기
            logger.error { "User not found : $id" }
            throw BusinessException(StatusCode.USER_NOT_FOUND)
        }

    fun getByEmail(email: String): User =
        userRepository.findByEmail(email)?.toDomain() ?: throw BusinessException(
            StatusCode.USER_NOT_FOUND,
        )

    @Transactional
    fun saveOrGetUser(user: User): User = userRepository.findByEmail(user.email)?.toDomain() ?: save(user)

    fun findByEmail(email: String): User? = userRepository.findByEmail(email)?.toDomain()

    fun findAllByUserIds(userIds: List<String>): List<User> = userRepository.findAllByIdIn(userIds).map { it.toDomain() }

    fun getAllByUserEmails(userIds: List<String>): List<User> {
        val users = userRepository.findAllByEmailIn(userIds).map { it.toDomain() }

        if (userIds.size != users.size) {
            throw BusinessException(StatusCode.USER_NOT_FOUND)
        }

        return users
    }
}
