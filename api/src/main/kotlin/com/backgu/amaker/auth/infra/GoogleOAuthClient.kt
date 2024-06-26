package com.backgu.amaker.auth.infra

import com.backgu.amaker.auth.dto.oauth.google.GoogleOAuth2AccessTokenDto
import com.backgu.amaker.config.CaughtHttpExchange
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.PostExchange

@CaughtHttpExchange
interface GoogleOAuthClient {
    @PostExchange("/token", contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    fun getGoogleOAuth2(
        @RequestParam("code") authCode: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("grant_type") grantType: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("client_id") clientId: String,
    ): GoogleOAuth2AccessTokenDto?
}
