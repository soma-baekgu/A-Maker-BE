package com.backgu.amaker.security.jwt.service

import com.auth0.jwt.exceptions.JWTDecodeException
import com.backgu.amaker.fixture.UserFixture
import com.backgu.amaker.user.domain.UserRole
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.Test

@DisplayName("JwtService 테스트")
@ExtendWith(SpringExtension::class)
@SpringBootTest
class JwtServiceTest {
    @Autowired
    lateinit var jwtService: JwtService

    @Test
    @DisplayName("토큰 생성 테스트")
    fun createToken() {
        // given
        val userId = UserFixture.defaultUserId
        val userRole = UserRole.USER.key

        // when
        val token = jwtService.create(userId, userRole)

        // then
        Assertions.assertThat(token).isNotNull()
    }

    @Test
    @DisplayName("토큰 검증 테스트")
    fun verifyToken() {
        // given
        val userId = UserFixture.defaultUserId
        val userRole = UserRole.USER.key
        val token = jwtService.create(userId, userRole)

        // when
        val verify = jwtService.verify(token)

        // then
        Assertions.assertThat(verify).isNotNull()
    }

    @Test
    @DisplayName("토큰 검증 실패 테스트")
    fun verifyTokenFail() {
        // given
        val token = "invalid token"

        // when & then
        Assertions
            .assertThatThrownBy { jwtService.verify(token) }
            .isInstanceOf(JWTDecodeException::class.java)
            .hasMessage("The token was expected to have 3 parts, but got 1.")
    }
}
