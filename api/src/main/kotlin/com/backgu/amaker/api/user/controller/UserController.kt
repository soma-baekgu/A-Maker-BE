package com.backgu.amaker.api.user.controller

import com.backgu.amaker.api.user.dto.request.EmailExistsRequest
import com.backgu.amaker.api.user.dto.request.UserDeviceCreateRequest
import com.backgu.amaker.api.user.dto.response.EmailExistsResponse
import com.backgu.amaker.api.user.service.UserFacadeService
import com.backgu.amaker.common.http.ApiHandler
import com.backgu.amaker.common.http.response.ApiResult
import com.backgu.amaker.common.security.jwt.authentication.JwtAuthentication
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userFacadeService: UserFacadeService,
    private val apiHandler: ApiHandler,
) : UserSwagger {
    @GetMapping("/email/exists")
    override fun checkEmail(
        @ModelAttribute @Valid emailExistsRequest: EmailExistsRequest,
    ): ResponseEntity<ApiResult<EmailExistsResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                EmailExistsResponse.of(
                    userFacadeService.existsByEmail(emailExistsRequest.email),
                ),
            ),
        )

    @PostMapping("/devices")
    override fun registerUserDevice(
        @AuthenticationPrincipal token: JwtAuthentication,
        @RequestBody @Valid userDeviceDto: UserDeviceCreateRequest,
    ): ResponseEntity<Void> {
        userFacadeService.registerUserDevice(userDeviceDto.toDto(userId = token.id))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
