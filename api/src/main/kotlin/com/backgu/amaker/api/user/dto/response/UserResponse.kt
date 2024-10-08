package com.backgu.amaker.api.user.dto.response

import com.backgu.amaker.api.user.dto.UserDto
import io.swagger.v3.oas.annotations.media.Schema

data class UserResponse(
    @Schema(description = "채팅유저 이름", example = "이승환")
    val name: String,
    @Schema(description = "채팅유저 이메일", example = "dltmd202@gmail.com")
    val email: String,
    @Schema(description = "채팅유저 프로필 사진", example = "http://127.0.0.1/images/default_thumbnail.png")
    val picture: String,
) {
    companion object {
        fun of(user: UserDto): UserResponse =
            UserResponse(
                name = user.name,
                email = user.email,
                picture = user.picture,
            )
    }
}
