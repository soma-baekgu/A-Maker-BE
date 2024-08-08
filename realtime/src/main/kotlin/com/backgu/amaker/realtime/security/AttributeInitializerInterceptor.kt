package com.backgu.amaker.realtime.security

import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.realtime.common.excpetion.RealTimeException
import com.backgu.amaker.realtime.ws.constants.WebSocketConstants.Companion.USER_ID
import com.backgu.amaker.realtime.ws.constants.WebSocketConstants.Companion.WORKSPACE_ID
import com.backgu.amaker.realtime.ws.constants.WebSocketConstants.Companion.WS_WORKSPACE_ENDPOINT
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.OriginHandshakeInterceptor

@Component
class AttributeInitializerInterceptor : OriginHandshakeInterceptor() {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>,
    ): Boolean {
        super.beforeHandshake(request, response, wsHandler, attributes)

        val principal: JwtAuthentication = extractPrincipal()
        val pathAttributes: Map<String, String> = extractPathParameters(request.uri.path, WS_WORKSPACE_ENDPOINT)

        attributes[USER_ID] = principal.id
        attributes[WORKSPACE_ID] =
            pathAttributes[WORKSPACE_ID]?.let { toLong(it) } ?: throw RealTimeException(StatusCode.INVALID_INPUT_VALUE)

        return true
    }

    fun toLong(value: String): Long =
        try {
            value.toLong()
        } catch (e: NumberFormatException) {
            throw RealTimeException(StatusCode.INVALID_INPUT_VALUE)
        }

    fun extractPrincipal(): JwtAuthentication {
        val principal: Any =
            SecurityContextHolder.getContext().authentication.principal
        if (principal is JwtAuthentication) return principal

        throw RealTimeException(StatusCode.UNAUTHORIZED)
    }

    fun extractVariableNames(pattern: String): List<String> = "\\{([^{}]*)\\}".toRegex().findAll(pattern).map { it.groupValues[1] }.toList()

    fun extractPathParameters(
        path: String,
        pattern: String,
    ): Map<String, String> {
        val variableNames = extractVariableNames(pattern)
        var regexPattern = pattern
        variableNames.forEach { name ->
            regexPattern = regexPattern.replace("{$name}", "([^/]+)")
        }
        val regex = Regex(regexPattern)
        val matchResult = regex.find(path)

        return if (matchResult != null && matchResult.groupValues.size == variableNames.size + 1) {
            variableNames.zip(matchResult.groupValues.drop(1)).toMap()
        } else {
            emptyMap()
        }
    }
}
