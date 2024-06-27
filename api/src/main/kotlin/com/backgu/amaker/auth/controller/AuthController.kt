package com.backgu.amaker.auth.controller

import com.backgu.amaker.auth.config.AuthConfig
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/auth")
class AuthController(
    val authConfig: AuthConfig,
) {
    @GetMapping("/oauth/google")
    fun googleAuth(response: HttpServletResponse) {
        response.sendRedirect(authConfig.oauthUrl())
    }
}
