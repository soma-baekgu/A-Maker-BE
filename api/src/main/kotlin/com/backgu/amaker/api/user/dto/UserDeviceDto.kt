package com.backgu.amaker.api.user.dto

import com.backgu.amaker.domain.user.Device
import com.backgu.amaker.domain.user.UserDevice

data class UserDeviceDto(
    val userId: String,
    val device: Device,
    val token: String,
) {
    companion object {
        fun of(userDevice: UserDevice) =
            UserDeviceDto(
                userId = userDevice.userId,
                device = userDevice.device,
                token = userDevice.token,
            )
    }

    fun toDomain() =
        UserDevice(
            userId = userId,
            device = device,
            token = token,
        )
}
