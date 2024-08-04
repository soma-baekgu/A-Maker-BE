package com.backgu.amaker.api.chat.interceptor

import com.backgu.amaker.common.security.jwt.utils.JwtAuthenticationTokenExtractor
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.regex.Pattern

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
class JwtChannelInterceptor(
    private val jwtAuthenticationTokenExtractor: JwtAuthenticationTokenExtractor,
) : ChannelInterceptor {
    private val bearerRegex: Pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE)
    private val headerKey: String = "Authorization"

    override fun preSend(
        message: Message<*>,
        channel: MessageChannel,
    ): Message<*> {
        val headerAccessor = StompHeaderAccessor.wrap(message)

        if (StompCommand.CONNECT == headerAccessor.command) {
            try {
                val authorizationToken: String =
                    obtainAuthorizationToken(headerAccessor)
                        ?: throw InsufficientAuthenticationException("Authorization token is null.")
                val authentication = jwtAuthenticationTokenExtractor.extractJwtAuthenticationToken(authorizationToken)
                SecurityContextHolder.getContext().authentication = authentication
                StompHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)?.let {
                    it.user = authentication
                }
            } catch (e: Exception) {
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
}
