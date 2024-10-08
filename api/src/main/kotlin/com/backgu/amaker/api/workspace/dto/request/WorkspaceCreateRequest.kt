package com.backgu.amaker.api.workspace.dto.request

import com.backgu.amaker.api.common.validator.ValidEmailList
import com.backgu.amaker.api.workspace.dto.WorkspaceCreateDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty

data class WorkspaceCreateRequest(
    @field:NotEmpty(message = "워크스페이스 이름은 필수입니다.")
    @Schema(description = "워크스페이스 이름", example = "백구팀 워크스페이스")
    val name: String,
    @field:ValidEmailList(message = "이메일을 확인해주세요.")
    val inviteesEmails: Set<String> = emptySet(),
) {
    fun toDto() =
        WorkspaceCreateDto(
            name = name,
            inviteesEmails = inviteesEmails,
        )
}
