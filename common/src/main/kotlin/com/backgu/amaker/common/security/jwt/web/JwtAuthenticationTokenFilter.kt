package com.backgu.amaker.common.security.jwt.web

import com.backgu.amaker.common.security.jwt.authentication.JwtAuthenticationToken
import com.backgu.amaker.common.security.jwt.utils.JwtAuthenticationTokenExtractor
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.regex.Pattern

class JwtAuthenticationTokenFilter(
    private val jwtAuthenticationTokenExtractor: JwtAuthenticationTokenExtractor,
) : OncePerRequestFilter() {
    private val bearerRegex: Pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE)
    private val headerKey: String = "Authorization"

    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain,
    ) {
        if (!isAlreadyAuthenticated()) {
            try {
                val authorizationToken: String = obtainAuthorizationToken(req)
                val authentication: JwtAuthenticationToken =
                    jwtAuthenticationTokenExtractor.extractJwtAuthenticationToken(authorizationToken)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(req)
                SecurityContextHolder.getContext().authentication = authentication
            } catch (e: AuthenticationException) {
                SecurityContextHolder.clearContext()
            }
        }

        chain.doFilter(req, res)
    }

    private fun isAlreadyAuthenticated(): Boolean = SecurityContextHolder.getContext().authentication != null

    private fun obtainAuthorizationToken(request: HttpServletRequest): String {
        var token: String =
            request.getHeader(headerKey) ?: throw UsernameNotFoundException("Authorization token is null.")
        token = URLDecoder.decode(token, "UTF-8") ?: throw UsernameNotFoundException("Invalid token")

        try {
            token = URLDecoder.decode(token, "UTF-8")
            val parts: Array<String> = token.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (parts.size == 2) {
                val scheme = parts[0]
                val credentials = parts[1]
                return if (bearerRegex.matcher(scheme).matches()) {
                    credentials
                } else {
                    throw UsernameNotFoundException("Invalid token")
                }
            } else {
                throw UsernameNotFoundException("Invalid token")
            }
        } catch (_: UnsupportedEncodingException) {
            throw UsernameNotFoundException("Invalid token")
        }
    }
}
