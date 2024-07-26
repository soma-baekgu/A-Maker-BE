package com.backgu.amaker.infra.jpa.user.reposotory

import com.backgu.amaker.infra.jpa.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByEmail(email: String): UserEntity?

    fun existsByEmail(email: String): Boolean

    fun findAllByIdIn(userIds: List<String>): List<UserEntity>

    fun findAllByEmailIn(userEmails: List<String>): List<UserEntity>
}
