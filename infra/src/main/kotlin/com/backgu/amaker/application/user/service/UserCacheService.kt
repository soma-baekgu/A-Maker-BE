package com.backgu.amaker.application.user.service

import com.backgu.amaker.domain.user.User
import com.backgu.amaker.infra.redis.user.data.UserCache
import com.backgu.amaker.infra.redis.user.repository.UserCacheRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserCacheService(
    private val userCacheRepository: UserCacheRepository,
) {
    fun getUserById(id: String): User? = userCacheRepository.findByIdOrNull(id)?.toDomain()

    fun save(user: User) = userCacheRepository.save(UserCache.of(user)).toDomain()

    fun findAllByUserIds(userIds: List<String>): List<User> = userCacheRepository.findAllById(userIds).map { it.toDomain() }
}
