package com.backgu.amaker.infra.jpa.user.entity

import com.backgu.amaker.domain.user.Device
import com.backgu.amaker.domain.user.UserDevice
import com.backgu.amaker.infra.jpa.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity(name = "UserDevice")
@Table(name = "user_device")
class UserDeviceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @Column(nullable = false)
    val userId: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val device: Device,
    @Column(nullable = false, unique = true)
    val deviceToken: String,
) : BaseTimeEntity() {
    fun toDomain(): UserDevice =
        UserDevice(
            id = id,
            userId = userId,
            device = device,
            token = deviceToken,
        )

    companion object {
        fun of(userDevice: UserDevice): UserDeviceEntity =
            UserDeviceEntity(
                id = userDevice.id,
                userId = userDevice.userId,
                device = userDevice.device,
                deviceToken = userDevice.token,
            )
    }
}
