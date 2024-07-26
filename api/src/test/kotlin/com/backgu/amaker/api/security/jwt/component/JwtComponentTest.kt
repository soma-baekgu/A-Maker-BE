package com.backgu.amaker.api.security.jwt.component

import com.auth0.jwt.exceptions.JWTDecodeException
import com.backgu.amaker.domain.user.UserRole
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
class JwtComponentTest {
    @Autowired
    lateinit var jwtComponent: JwtComponent

    @Test
    @DisplayName("토큰 생성 테스트")
    fun createToken() {
        // given
        val userId = "tester"
        val userRole = UserRole.USER.key

        // when
        val token = jwtComponent.create(userId, userRole)

        // then
        Assertions.assertThat(token).isNotNull()
    }

    @Test
    @DisplayName("토큰 검증 테스트")
    fun verifyToken() {
        // given
        val userId = "tester"
        val userRole = UserRole.USER.key
        val token = jwtComponent.create(userId, userRole)

        // when
        val verify = jwtComponent.verify(token)

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
            .assertThatThrownBy { jwtComponent.verify(token) }
            .isInstanceOf(JWTDecodeException::class.java)
            .hasMessage("The token was expected to have 3 parts, but got 1.")
    }
}
