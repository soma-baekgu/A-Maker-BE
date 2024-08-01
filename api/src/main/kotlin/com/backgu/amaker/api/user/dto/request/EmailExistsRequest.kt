package com.backgu.amaker.api.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class EmailExistsRequest(
    @field:NotEmpty
    @field:Email
    @Schema(description = "확인할 이메일", example = "abc@example.com")
    val email: String,
)
