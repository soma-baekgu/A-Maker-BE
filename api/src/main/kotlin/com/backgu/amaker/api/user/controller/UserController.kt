package com.backgu.amaker.api.user.controller

import com.backgu.amaker.api.common.dto.response.ApiResult
import com.backgu.amaker.api.common.infra.ApiHandler
import com.backgu.amaker.api.user.dto.response.EmailExistsResponse
import com.backgu.amaker.api.user.service.UserFacadeService
import jakarta.validation.constraints.Email
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userFacadeService: UserFacadeService,
    private val apiHandler: ApiHandler,
) : UserSwagger {
    @GetMapping("/email/exists")
    override fun checkEmail(
        @RequestParam @Email email: String,
    ): ResponseEntity<ApiResult<EmailExistsResponse>> =
        ResponseEntity.ok().body(
            apiHandler.onSuccess(
                EmailExistsResponse.of(
                    userFacadeService.existsByEmail(email),
                ),
            ),
        )
}
