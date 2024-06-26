package com.backgu.amaker.security

import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.domain.UserRole
import com.backgu.amaker.user.service.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils.createAuthorityList
import org.springframework.util.ClassUtils

class JwtAuthenticationProvider(
    private val userService: UserService,
) : AuthenticationProvider {
    override fun supports(authentication: Class<*>?): Boolean =
        ClassUtils.isAssignable(JwtAuthenticationToken::class.java, authentication!!)

    override fun authenticate(authentication: Authentication): Authentication =
        processOAuthAuthentication(
            authentication.principal.toString(),
        )

    private fun processOAuthAuthentication(email: String): Authentication {
        // TODO exception handling
        val user: User = userService.getByEmail(email) ?: throw IllegalArgumentException("User not found")
        val authenticated: JwtAuthenticationToken =
            JwtAuthenticationToken(
                JwtAuthentication(user.id, user.name),
                createAuthorityList(UserRole.USER.key),
            )
        authenticated.details = user
        return authenticated
    }
}
