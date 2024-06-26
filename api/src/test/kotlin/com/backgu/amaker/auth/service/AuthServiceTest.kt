package com.backgu.amaker.auth.service

import com.backgu.amaker.auth.infra.GoogleApiClient
import com.backgu.amaker.auth.infra.GoogleOAuthClient
import com.backgu.amaker.auth.test.FailedFakeGoogleApiClient
import com.backgu.amaker.auth.test.FailedFakeGoogleOAuthClient
import com.backgu.amaker.auth.test.SuccessfulStubGoogleApiClient
import com.backgu.amaker.auth.test.SuccessfulStubGoogleOAuthClient
import com.backgu.amaker.fixture.AuthFixture
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AuthServiceTest {
    private lateinit var authService: AuthService
    private lateinit var googleOAuthClient: GoogleOAuthClient
    private lateinit var googleApiClient: GoogleApiClient

    @Test
    @DisplayName("구글 성공 로그인 테스트")
    fun successfulGoogleLoginTest() {
        // given
        val email = "abc@gmail.com"
        googleOAuthClient = SuccessfulStubGoogleOAuthClient()
        googleApiClient = SuccessfulStubGoogleApiClient(email)
        authService = AuthService(googleOAuthClient, googleApiClient, AuthFixture.createUserRequest())

        // when
        val result = authService.googleLogin("authCode")

        // then
        assertThat(result.email).isEqualTo(email)
    }

    @Test
    @DisplayName("구글 oauth 서버에서 토큰 획득 실패 테스트")
    fun failedToGetAccessTokenTest() {
        // given
        val email = "abc@gmail.com"
        googleOAuthClient = FailedFakeGoogleOAuthClient()
        googleApiClient = SuccessfulStubGoogleApiClient(email)
        authService = AuthService(googleOAuthClient, googleApiClient, AuthFixture.createUserRequest())

        // when
        // then
        assertThatThrownBy { authService.googleLogin("authCode") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Failed to get access token")
    }

    @Test
    @DisplayName("구글 oauth 서버에서 토큰 획득 실패 테스트")
    fun failedToUserInfo() {
        // given
        val email = "abc@gmail.com"
        googleOAuthClient = SuccessfulStubGoogleOAuthClient()
        googleApiClient = FailedFakeGoogleApiClient()
        authService = AuthService(googleOAuthClient, googleApiClient, AuthFixture.createUserRequest())

        // when
        // then
        assertThatThrownBy { authService.googleLogin("authCode") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Failed to get user information")
    }
}
