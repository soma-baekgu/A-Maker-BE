package com.backgu.amaker.api.auth.service

import com.backgu.amaker.api.auth.dto.JwtTokenDto
import com.backgu.amaker.api.auth.dto.oauth.google.GoogleUserInfoDto
import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.common.id.IdPublisher
import com.backgu.amaker.common.security.jwt.component.JwtComponent
import com.backgu.amaker.domain.user.User
import org.springframework.stereotype.Service

@Service
class AuthFacadeService(
    val oauthService: AuthService,
    val jwtComponent: JwtComponent,
    val userService: UserService,
    val idPublisher: IdPublisher,
) {
    fun googleLogin(authorizationCode: String): JwtTokenDto {
        val userInfo: GoogleUserInfoDto = oauthService.googleLogin(authorizationCode)
        val savedUser: User =
            userService.saveOrGetUser(userInfo.toDomain(idPublisher))
        val token: String = jwtComponent.create(savedUser.id, savedUser.userRole.key)

        return JwtTokenDto.of(token, savedUser)
    }
}
