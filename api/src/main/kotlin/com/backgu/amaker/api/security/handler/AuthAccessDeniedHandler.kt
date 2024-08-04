package com.backgu.amaker.api.security.handler

import com.backgu.amaker.api.common.infra.ApiHandler
import com.backgu.amaker.common.status.StatusCode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class AuthAccessDeniedHandler(
    private val apiHandler: ApiHandler,
    private val objectMapper: ObjectMapper,
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        val errorMessage = apiHandler.onFailure<Void>(StatusCode.ACCESS_DENIED)

        val jsonResponse = objectMapper.writeValueAsString(errorMessage)
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(jsonResponse)
    }
}
