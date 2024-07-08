package com.backgu.amaker.chat.interceptor

import com.backgu.amaker.security.JwtAuthentication
import com.backgu.amaker.security.JwtAuthenticationToken
import com.backgu.amaker.security.jwt.component.JwtComponent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.regex.Pattern

private val logger = KotlinLogging.logger {}

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
class JwtChannelInterceptor(
    private val jwtComponent: JwtComponent,
) : ChannelInterceptor {
    private val bearerRegex: Pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE)
    private val headerKey: String = "Authorization"

    override fun preSend(
        message: Message<*>,
        channel: MessageChannel,
    ): Message<*> {
        val headerAccessor = StompHeaderAccessor.wrap(message)
        logger.info { "전체 메시지: $message" }

        if (StompCommand.CONNECT == headerAccessor.command) {
            try {
                val authorizationToken: String =
                    obtainAuthorizationToken(headerAccessor)
                        ?: throw InsufficientAuthenticationException("Authorization token is null.")

                val claims: JwtComponent.Claims = jwtComponent.verify(authorizationToken)

                val id: String = claims.id.replace("\"", "")
                val authorities: List<GrantedAuthority> = obtainAuthorities(claims)

                if (id.isNotEmpty() && authorities.isNotEmpty()) {
                    val authentication =
                        JwtAuthenticationToken(
                            JwtAuthentication(id, authorizationToken),
                            authorities,
                        )
                    SecurityContextHolder.getContext().authentication = authentication
                    headerAccessor.user = authentication
                } else {
                    throw InsufficientAuthenticationException("Invalid id or authorities are empty.")
                }
            } catch (e: Exception) {
                logger.error { "인증에 실패했습니다: ${e.message}" }
                throw InsufficientAuthenticationException("인증에 실패했습니다.", e)
            }
        }

        // 메시지를 그대로 반환
        return message
    }

    private fun obtainAuthorizationToken(headerAccessor: StompHeaderAccessor): String? =
        try {
            val authHeader =
                headerAccessor.getNativeHeader(headerKey)
                    ?: throw InsufficientAuthenticationException("Authorization header is missing.")

            var token: String? = authHeader[0]
            token = URLDecoder.decode(token, "UTF-8")
            val parts = token.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (parts.size == 2) {
                val scheme = parts[0]
                val credentials = parts[1]
                if (bearerRegex.matcher(scheme).matches()) credentials else null
            } else {
                null
            }
        } catch (e: UnsupportedEncodingException) {
            throw InsufficientAuthenticationException("Failed to decode authorization token.", e)
        }

    private fun obtainAuthorities(claims: JwtComponent.Claims): List<GrantedAuthority> {
        val roles: Array<String> = claims.roles
        if (roles.isEmpty()) {
            throw InsufficientAuthenticationException("Roles are empty in claims.")
        }

        return roles.map { role ->
            SimpleGrantedAuthority(role)
        }
    }
}
