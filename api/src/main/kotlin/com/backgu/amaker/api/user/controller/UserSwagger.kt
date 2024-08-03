package com.backgu.amaker.api.user.controller

import com.backgu.amaker.api.common.dto.response.ApiResult
import com.backgu.amaker.api.user.dto.request.EmailExistsRequest
import com.backgu.amaker.api.user.dto.response.EmailExistsResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Email
import org.springframework.http.ResponseEntity

@Tag(name = "users", description = "유저 API")
interface UserSwagger {
    @Operation(summary = "가입된 이메일 확인", description = "가입된 이메일인지 확인합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "이메일 확인 성공",
            ),
        ],
    )
    fun checkEmail(
        @Email email: EmailExistsRequest,
    ): ResponseEntity<ApiResult<EmailExistsResponse>>
}
