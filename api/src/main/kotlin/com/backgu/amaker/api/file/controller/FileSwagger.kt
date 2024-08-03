package com.backgu.amaker.api.file.controller

import com.backgu.amaker.api.common.dto.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "file", description = "파일 API")
interface FileSwagger {
    @Operation(summary = "파일 저장 URL 생성", description = "파일 저장용 presigned URL을 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "파일 URL 생성 성공",
            ),
        ],
    )
    fun generateFileSaveUrl(
        @AuthenticationPrincipal token: JwtAuthentication,
        @RequestParam(name = "path") path: String,
        @RequestParam(name = "extension") extension: String,
        @RequestParam(required = false) name: String?,
    ): ResponseEntity<ApiResult<String>>
}
