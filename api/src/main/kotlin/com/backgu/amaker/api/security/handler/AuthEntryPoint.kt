package com.backgu.amaker.api.security.handler

import com.backgu.amaker.api.common.infra.ApiHandler
import com.backgu.amaker.common.status.StatusCode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthEntryPoint(
    private val apiHandler: ApiHandler,
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val errorMessage = apiHandler.onFailure<Void>(StatusCode.UNAUTHORIZED)

        val jsonResponse = objectMapper.writeValueAsString(errorMessage)
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(jsonResponse)
    }
}
