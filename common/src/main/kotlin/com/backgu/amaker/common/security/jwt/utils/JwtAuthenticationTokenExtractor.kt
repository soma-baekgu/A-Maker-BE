package com.backgu.amaker.common.security.jwt.utils

import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthenticationToken
import com.backgu.amaker.common.security.jwt.component.JwtComponent
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.Arrays
import java.util.stream.Collectors

class JwtAuthenticationTokenExtractor(
    private val jwtComponent: JwtComponent,
) {
    fun extractJwtAuthenticationToken(token: String): JwtAuthenticationToken {
        val claims: JwtComponent.Claims = jwtComponent.verify(token)

        val id: String = claims.id.replace("\"", "")
        val authorities: List<GrantedAuthority> = obtainAuthorities(claims)

        if (authorities.isEmpty()) throw UsernameNotFoundException("Invalid token")
        return JwtAuthenticationToken(
            JwtAuthentication(id, token),
            authorities,
        )
    }

    private fun obtainAuthorities(claims: JwtComponent.Claims): List<GrantedAuthority> {
        val roles: Array<String> = claims.roles
        return if (roles.isEmpty()) {
            emptyList()
        } else {
            Arrays
                .stream(roles)
                .map { role: String? ->
                    SimpleGrantedAuthority(
                        role,
                    )
                }.collect(Collectors.toList())
        }
    }
}
