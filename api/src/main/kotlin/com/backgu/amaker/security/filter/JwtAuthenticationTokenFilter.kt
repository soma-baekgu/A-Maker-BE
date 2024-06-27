package com.backgu.amaker.security.filter

import com.backgu.amaker.security.JwtAuthentication
import com.backgu.amaker.security.JwtAuthenticationToken
import com.backgu.amaker.security.jwt.component.JwtComponent
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.Arrays
import java.util.Objects
import java.util.UUID
import java.util.regex.Pattern
import java.util.stream.Collectors

class JwtAuthenticationTokenFilter(
    private val jwtComponent: JwtComponent,
) : OncePerRequestFilter() {
    private val bearerRegex: Pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE)
    private val headerKey: String = "Authorization"

    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain,
    ) {
        if (SecurityContextHolder.getContext().authentication == null) {
            val authorizationToken: String? = obtainAuthorizationToken(req)
            try {
                if (authorizationToken != null) {
                    val claims: JwtComponent.Claims = jwtComponent.verify(authorizationToken)

                    val id: UUID = UUID.fromString(claims.id.replace("\"", ""))
                    val authorities: List<GrantedAuthority> = obtainAuthorities(claims)

                    if (Objects.nonNull(id) && authorities.isNotEmpty()) {
                        val authentication: JwtAuthenticationToken =
                            JwtAuthenticationToken(
                                JwtAuthentication(id, authorizationToken),
                                authorities,
                            )
                        authentication.details = WebAuthenticationDetailsSource().buildDetails(req)
                        SecurityContextHolder.getContext().authentication = authentication
                    }
                }
            } catch (e: Exception) {
                // TODO exception handling
            }
        }
        chain.doFilter(req, res)
    }

    private fun obtainAuthorizationToken(request: HttpServletRequest): String? {
        var token: String? = request.getHeader(headerKey)
        if (token != null) {
            try {
                token = URLDecoder.decode(token, "UTF-8")
                val parts: Array<String> = token.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (parts.size == 2) {
                    val scheme = parts[0]
                    val credentials = parts[1]
                    return if (bearerRegex.matcher(scheme).matches()) credentials else null
                }
            } catch (_: UnsupportedEncodingException) {
                // TODO exception handling
            }
        }

        return null
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
