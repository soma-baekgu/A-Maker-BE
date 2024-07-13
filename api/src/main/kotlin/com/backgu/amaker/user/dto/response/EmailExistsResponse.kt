package com.backgu.amaker.user.dto.response

import com.backgu.amaker.user.dto.EmailExistsDto
import io.swagger.v3.oas.annotations.media.Schema

data class EmailExistsResponse(
    @Schema(description = "가입된 이메일 유무", example = "true")
    val exists: Boolean,
) {
    companion object {
        fun of(emailExistsDto: EmailExistsDto): EmailExistsResponse = EmailExistsResponse(exists = emailExistsDto.exists)
    }
}
