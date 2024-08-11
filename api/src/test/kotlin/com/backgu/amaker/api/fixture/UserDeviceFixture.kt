package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.user.Device
import com.backgu.amaker.domain.user.UserDevice
import com.backgu.amaker.infra.jpa.user.entity.UserDeviceEntity
import com.backgu.amaker.infra.jpa.user.reposotory.UserDeviceRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserDeviceFixture(
    private val userDeviceRepository: UserDeviceRepository,
) {
    companion object {
        fun createUserDevice(
            userId: String,
            token: String = UUID.randomUUID().toString(),
            device: Device = Device.ANDROID,
        ): UserDevice =
            UserDevice(
                userId = userId,
                device = device,
                token = token,
            )
    }

    fun createPersistedUserDevice(
        userId: String = UUID.randomUUID().toString(),
        token: String = UUID.randomUUID().toString(),
        device: Device = Device.ANDROID,
    ) = userDeviceRepository
        .save(
            UserDeviceEntity.of(createUserDevice(userId, token, device)),
        ).toDomain()
}
