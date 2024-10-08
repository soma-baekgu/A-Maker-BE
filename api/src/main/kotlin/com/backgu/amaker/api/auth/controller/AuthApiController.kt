package com.backgu.amaker.api.auth.controller

import com.backgu.amaker.api.auth.config.AuthConfig
import com.backgu.amaker.api.auth.dto.response.JwtTokenResponse
import com.backgu.amaker.api.auth.dto.response.OAuthUrlResponse
import com.backgu.amaker.api.auth.service.AuthFacadeService
import com.backgu.amaker.common.http.ApiHandler
import com.backgu.amaker.common.http.response.ApiResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthApiController(
    val authConfig: AuthConfig,
    val authFacadeService: AuthFacadeService,
    val apiHandler: ApiHandler,
) : AuthApiSwagger {
    @GetMapping("/oauth/google")
    override fun googleAuth(): ResponseEntity<ApiResult<OAuthUrlResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                OAuthUrlResponse(authConfig.oauthUrl()),
            ),
        )

    @PostMapping("/code/google")
    override fun login(
        @RequestParam(name = "code") authorizationCode: String,
    ): ResponseEntity<ApiResult<JwtTokenResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                JwtTokenResponse.of(authFacadeService.googleLogin(authorizationCode)),
            ),
        )
}
