package com.backgu.amaker.auth.service

import com.backgu.amaker.auth.dto.JwtTokenResponse
import com.backgu.amaker.auth.dto.oauth.google.GoogleUserInfoDto
import com.backgu.amaker.security.jwt.component.JwtComponent
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.user.service.UserService
import org.springframework.stereotype.Service

@Service
class AuthFacadeService(
    val oauthService: AuthService,
    val jwtComponent: JwtComponent,
    val userService: UserService,
) {
    fun googleLogin(authorizationCode: String): JwtTokenResponse {
        val userInfo: GoogleUserInfoDto = oauthService.googleLogin(authorizationCode)
        val savedUser: User =
            userService.saveOrGetUser(userInfo.toDomain())
        val token: String = jwtComponent.create(savedUser.id, savedUser.userRole.key)

        return JwtTokenResponse(token, savedUser)
    }
}
