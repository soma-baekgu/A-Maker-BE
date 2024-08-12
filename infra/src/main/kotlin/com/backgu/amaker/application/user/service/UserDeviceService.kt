package com.backgu.amaker.application.user.service

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.user.UserDevice
import com.backgu.amaker.infra.jpa.user.entity.UserDeviceEntity
import com.backgu.amaker.infra.jpa.user.reposotory.UserDeviceRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserDeviceService(
    private val userDeviceRepository: UserDeviceRepository,
) {
    @Transactional
    fun save(userDevice: UserDevice): UserDevice {
        try {
            val savedUserDevice = userDeviceRepository.save(UserDeviceEntity.of(userDevice))
            return savedUserDevice.toDomain()
        } catch (e: DataIntegrityViolationException) {
            throw BusinessException(StatusCode.USER_DEVICE_DUPLICATED)
        }
    }

    fun findByUserIds(userIds: List<String>): List<UserDevice> = userDeviceRepository.findByUserIds(userIds).map { it.toDomain() }
}
