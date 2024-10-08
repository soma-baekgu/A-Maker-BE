package com.backgu.amaker.application.user.service

import com.backgu.amaker.common.exception.BusinessException
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

    fun getAllByUserIds(userIds: List<String>): List<User> = getAllByUserIds(userIds.toSet())

    fun getAllByUserIds(userIds: Set<String>): List<User> {
        val users = userRepository.findAllByIdIn(userIds).map { it.toDomain() }
        if (userIds.size != users.size) {
            throw BusinessException(StatusCode.USER_NOT_FOUND)
        }
        return users
    }

    fun findAllByUserIdsToMap(userIds: List<String>): Map<String, User> =
        userRepository.findAllByIdIn(userIds.toSet()).map { it.toDomain() }.associateBy { it.id }

    fun getAllByUserEmails(userIds: List<String>): List<User> {
        val users = userRepository.findAllByEmailIn(userIds).map { it.toDomain() }
        if (userIds.size != users.size) {
            throw BusinessException(StatusCode.USER_NOT_FOUND)
        }
        return users
    }

    // TODO 테스트
    fun getByWorkspaceId(workspaceId: Long): List<User> {
        val users = userRepository.findByWorkspaceId(workspaceId).map { it.toDomain() }
        if (users.isEmpty()) throw BusinessException(StatusCode.USER_NOT_FOUND)
        return users
    }

    fun getByChatRoomId(chatRoomId: Long): List<User> {
        val users = userRepository.findByChatRoomId(chatRoomId).map { it.toDomain() }
        if (users.isEmpty()) throw BusinessException(StatusCode.USER_NOT_FOUND)
        return users
    }
}
