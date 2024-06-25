package com.backgu.amaker.user.repository

import com.backgu.amaker.user.domain.User
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
}
