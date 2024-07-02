package com.backgu.amaker.workspace.dto.request

import com.backgu.amaker.workspace.dto.WorkspaceCreateDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty

data class WorkspaceCreateRequest(
    @field:NotEmpty(message = "워크스페이스 이름은 필수입니다.")
    @Schema(description = "워크스페이스 이름", example = "백구팀 워크스페이스")
    val name: String,
) {
    fun toDto() =
        WorkspaceCreateDto(
            name = name,
        )
}
