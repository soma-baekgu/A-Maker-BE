package com.backgu.amaker.auth.service

import com.backgu.amaker.auth.dto.JwtTokenResponse
import com.backgu.amaker.auth.dto.oauth.google.GoogleUserInfoDto
import com.backgu.amaker.security.jwt.service.JwtService
import com.backgu.amaker.user.dto.UserCreateDto
import com.backgu.amaker.user.dto.UserDto
import com.backgu.amaker.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthFacade(
    val oauthService: OAuthService,
    val jwtService: JwtService,
    val userService: UserService,
) {
    @Transactional
    fun googleLogin(authorizationCode: String): JwtTokenResponse {
        val userInfo: GoogleUserInfoDto = oauthService.googleLogin(authorizationCode)
        val savedUser: UserDto =
            userService.saveOrGetUser(UserCreateDto(userInfo.name, userInfo.email, userInfo.picture))
        val token: String = jwtService.create(savedUser.id, savedUser.userRole.key)

        return JwtTokenResponse(token, savedUser)
    }
}
