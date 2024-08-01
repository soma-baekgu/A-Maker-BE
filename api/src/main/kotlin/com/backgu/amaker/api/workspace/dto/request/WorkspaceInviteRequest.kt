package com.backgu.amaker.api.workspace.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class WorkspaceInviteRequest(
    @field:NotEmpty
    @field:Email
    @Schema(description = "초대할 사용자 이메일", example = "abc@example.com")
    val email: String,
)
