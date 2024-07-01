package com.backgu.amaker.auth.controller

import com.backgu.amaker.auth.config.AuthConfig
import com.backgu.amaker.auth.dto.JwtTokenResponse
import com.backgu.amaker.auth.service.AuthFacadeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthApiController(
    val authConfig: AuthConfig,
    val authFacadeService: AuthFacadeService,
) : AuthApiSwagger {
    @GetMapping("/oauth/google")
    fun googleAuth(): String = authConfig.oauthUrl()

    @GetMapping("/code/google")
    fun login(
        @RequestParam(name = "code") authorizationCode: String,
        @RequestParam(name = "scope", required = false) scope: String,
        @RequestParam(name = "authuser", required = false) authUser: String,
        @RequestParam(name = "prompt", required = false) prompt: String,
    ): ResponseEntity<JwtTokenResponse> {
        val token: JwtTokenResponse = authFacadeService.googleLogin(authorizationCode)
        return ResponseEntity.ok(token)
    }
}
