package com.backgu.amaker.api.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class AuthAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        // TODO 후에 에러 폼이 수정되면 다시 작성
        response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.message)
    }
}
