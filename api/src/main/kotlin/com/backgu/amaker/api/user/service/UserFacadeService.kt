package com.backgu.amaker.api.user.service

import com.backgu.amaker.api.user.dto.EmailExistsDto
import com.backgu.amaker.api.user.dto.UserDeviceDto
import com.backgu.amaker.application.user.service.UserDeviceService
import com.backgu.amaker.application.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserFacadeService(
    private val userService: UserService,
    private val userDeviceService: UserDeviceService,
) {
    fun existsByEmail(email: String): EmailExistsDto = EmailExistsDto.of(user = userService.findByEmail(email))

    @Transactional
    fun registerUserDevice(userDeviceDto: UserDeviceDto): UserDeviceDto = UserDeviceDto.of(userDeviceService.save(userDeviceDto.toDomain()))
}
