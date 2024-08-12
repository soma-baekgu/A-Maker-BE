package com.backgu.amaker.api.user.controller

import com.backgu.amaker.api.user.dto.request.EmailExistsRequest
import com.backgu.amaker.api.user.dto.request.UserDeviceCreateRequest
import com.backgu.amaker.api.user.dto.response.EmailExistsResponse
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
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
    fun checkEmail(emailExistsRequest: EmailExistsRequest): ResponseEntity<ApiResult<EmailExistsResponse>>

    @Operation(summary = "fcm 디바이스 토큰 등록", description = "fcm 푸시 알림 전송용 토큰을 등록합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "디바이스 토큰 등록 성공",
            ),
        ],
    )
    fun registerUserDevice(
        token: JwtAuthentication,
        userDeviceDto: UserDeviceCreateRequest,
    ): ResponseEntity<Unit>
}
