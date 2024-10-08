package com.backgu.amaker.api.user.dto.request

import com.backgu.amaker.api.user.dto.UserDeviceDto
import com.backgu.amaker.domain.user.Device
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class UserDeviceCreateRequest(
    @field:NotNull(message = "디바이스 종류는 필수입니다.")
    @Schema(description = "디바이스 종류(IOS, ANDROID)", example = "ANDROID")
    val device: Device,
    @field:NotEmpty(message = "fcm 디바이스 토큰은 필수입니다.")
    @Schema(description = "fcm 디바이스 토큰", example = "jafelijefaeflkefkfeijeif")
    val token: String,
) {
    fun toDto(userId: String) =
        UserDeviceDto(
            userId = userId,
            device = device,
            token = token,
        )
}
