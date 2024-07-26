package com.backgu.amaker.api.auth.dto.response

import com.backgu.amaker.api.auth.dto.JwtTokenDto
import io.swagger.v3.oas.annotations.media.Schema

data class JwtTokenResponse(
    @Schema(
        description = "jwt token",
        example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYyMjEwNjQwMCwiZXhwIjoxNjIyMTA2NDAwfQ.3",
    )
    val token: String,
    @Schema(description = "유저 이름", example = "백구")
    val name: String,
    @Schema(description = "유저 이메일", example = "backgu9@gmail.com")
    val email: String,
    @Schema(description = "유저 사진", example = "https://example.com/picture.jpg")
    val picture: String,
) {
    companion object {
        fun of(jwtTokenDto: JwtTokenDto): JwtTokenResponse =
            JwtTokenResponse(
                jwtTokenDto.token,
                jwtTokenDto.name,
                jwtTokenDto.email,
                jwtTokenDto.picture,
            )
    }
}
