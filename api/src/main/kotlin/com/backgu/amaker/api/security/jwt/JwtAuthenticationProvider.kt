package com.backgu.amaker.api.security.jwt

import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthenticationToken
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.user.UserRole
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils.createAuthorityList
import org.springframework.stereotype.Component
import org.springframework.util.ClassUtils

@Component
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
        val user: User = userService.getByEmail(email)
        val authenticated: JwtAuthenticationToken =
            JwtAuthenticationToken(
                JwtAuthentication(user.id, user.name),
                createAuthorityList(UserRole.USER.key),
            )
        authenticated.details = user
        return authenticated
    }
}
