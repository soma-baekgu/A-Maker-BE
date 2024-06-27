package com.backgu.amaker.fixture

import com.backgu.amaker.auth.config.AuthConfig
import com.backgu.amaker.auth.dto.oauth.google.GoogleUserInfoDto

class AuthFixture {
    companion object {
        fun createUserRequest(): AuthConfig =
            AuthConfig().apply {
                clientId = "your-client-id"
                clientSecret = "your-client-secret"
                redirectUri = "http://localhost/callback"
                clientName = "your-client-name"
                baseUrl = "https://oauth2.googleapis.com"
                scope = "email,profile"
                oauthUrl = "https://oauth2.googleapis.com"
                apiUrl = "https://www.googleapis.com"
                grantType = "authorization_code"
            }

        fun createGoogleUserInfoDto(): GoogleUserInfoDto = GoogleUserInfoDto("test", "test@gmail.com", true, "tester", "tester", "tester")
    }
}
