package com.backgu.amaker.auth.service

import com.backgu.amaker.auth.dto.JwtTokenResponse
import com.backgu.amaker.fixture.AuthFixture
import com.backgu.amaker.security.jwt.component.JwtComponent
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@DisplayName("AuthFacadeTest 테스트")
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class AuthFacadeServiceTest {
    @Autowired
    lateinit var authFacadeService: AuthFacadeService

    @Autowired
    lateinit var jwtComponent: JwtComponent

    @MockkBean
    lateinit var oauthService: AuthService

    @Test
    @DisplayName("googleLogin 테스트")
    fun googleLoginTest() {
        // given
        every { oauthService.googleLogin(any()) } returns AuthFixture.createGoogleUserInfoDto()

        // then
        val googleLogin: JwtTokenResponse = authFacadeService.googleLogin("authCode")

        // then
        assertThat(jwtComponent.verify(googleLogin.token)).isNotNull()
    }
}
