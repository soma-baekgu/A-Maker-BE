package com.backgu.amaker.api.chat.config

import com.backgu.amaker.api.chat.annotation.ChattingLoginUser
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthenticationToken
import org.springframework.core.MethodParameter
import org.springframework.messaging.Message
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageHeaderAccessor

class ChattingUserIdArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.getParameterType() == JwtAuthentication::class.java &&
            parameter.hasParameterAnnotation(
                ChattingLoginUser::class.java,
            )

    override fun resolveArgument(
        parameter: MethodParameter,
        message: Message<*>,
    ): JwtAuthentication? {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
        val principal = accessor?.user
        if (principal is JwtAuthenticationToken) return principal.principal
        return null
    }
}
