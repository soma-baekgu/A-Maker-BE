package com.backgu.amaker.api.event.dto.request

import com.backgu.amaker.api.event.dto.TaskCommentCreateDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.net.URI

data class TaskCommentCreateRequest(
    @field:NotBlank(message = "task 응답 파일 경로는 필수입니다.")
    @Schema(description = "파일 경로", example = "https://a-maker.co.kr/hi.png")
    val path: String,
) {
    fun toDto(): TaskCommentCreateDto {
        val uri = URI(path)
        val cleanPath = URI(uri.scheme, uri.authority, uri.path, null, null).toString()

        return TaskCommentCreateDto(
            path = cleanPath,
        )
    }
}
