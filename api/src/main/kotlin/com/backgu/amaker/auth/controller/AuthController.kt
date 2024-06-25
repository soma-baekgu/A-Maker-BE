package com.backgu.amaker.auth.controller

import com.backgu.amaker.auth.config.AuthConfig
import com.backgu.amaker.auth.service.AuthService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/auth")
class AuthController(
    val authConfig: AuthConfig,
    val authService: AuthService,
) {
    @GetMapping("/oauth/google")
    fun googleAuth(response: HttpServletResponse) {
        response.sendRedirect(authConfig.oauthUrl())
    }

    @GetMapping("/code/google")
    fun login(
        @RequestParam(name = "code") authorizationCode: String,
        @RequestParam(name = "scope", required = false) scope: String,
        @RequestParam(name = "authuser", required = false) authUser: String,
        @RequestParam(name = "prompt", required = false) prompt: String,
    ): String {
        authService.googleLogin(authorizationCode)
        return "redirect:/"
    }
}
