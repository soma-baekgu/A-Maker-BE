package com.backgu.amaker.api.user.service

import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.infra.jpa.user.entity.UserEntity
import com.backgu.amaker.infra.jpa.user.reposotory.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    fun getById(id: String): User = userRepository.findByIdOrNull(id)?.toDomain() ?: throw BusinessException(StatusCode.USER_NOT_FOUND)

    fun getByEmail(email: String): User =
        userRepository.findByEmail(email)?.toDomain() ?: throw BusinessException(
            StatusCode.USER_NOT_FOUND,
        )

    @Transactional
    fun saveOrGetUser(user: User): User = userRepository.findByEmail(user.email)?.toDomain() ?: save(user)

    fun findByEmail(email: String): User? = userRepository.findByEmail(email)?.toDomain()

    fun findAllByUserIds(userIds: List<String>): List<User> = userRepository.findAllByIdIn(userIds).map { it.toDomain() }

    fun findAllByUserIdsToMap(userIds: List<String>): Map<String, User> =
        userRepository.findAllByIdIn(userIds).map { it.toDomain() }.associateBy { it.id }

    fun getAllByUserEmails(userIds: List<String>): List<User> {
        val users = userRepository.findAllByEmailIn(userIds).map { it.toDomain() }

        if (userIds.size != users.size) {
            throw BusinessException(StatusCode.USER_NOT_FOUND)
        }

        return users
    }
}
