package com.backgu.amaker.auth.controller

import com.backgu.amaker.auth.dto.response.JwtTokenResponse
import com.backgu.amaker.auth.dto.response.OAuthUrlResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "auth", description = "인증 API")
interface AuthApiSwagger {
    @Operation(summary = "구글 oauth url 조회", description = "구글 oauth url을 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "구글 oauth url 조회 성공",
                content = [Content(schema = Schema(implementation = OAuthUrlResponse::class))],
            ),
        ],
    )
    fun googleAuth(): ResponseEntity<OAuthUrlResponse>

    @Operation(summary = "구글 로그인 요청", description = "구글 로그인을 요청합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "구글 로그인 성공",
                content = [Content(schema = Schema(implementation = JwtTokenResponse::class))],
            ),
        ],
    )
    fun login(
        authorizationCode: String,
        scope: String,
        authUser: String,
        prompt: String,
    ): ResponseEntity<JwtTokenResponse>
}
