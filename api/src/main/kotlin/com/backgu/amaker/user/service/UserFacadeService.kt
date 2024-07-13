package com.backgu.amaker.user.service

import com.backgu.amaker.user.dto.EmailExistsDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserFacadeService(
    private val userService: UserService,
) {
    fun existsByEmail(email: String): EmailExistsDto = EmailExistsDto.of(user = userService.findByEmail(email))
}
