package com.backgu.amaker.api.auth.infra

import com.backgu.amaker.api.auth.dto.oauth.google.GoogleUserInfoDto
import com.backgu.amaker.api.config.CaughtHttpExchange
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange

@CaughtHttpExchange
interface GoogleApiClient {
    @GetExchange("/oauth2/v2/userinfo")
    fun getUserInfo(
        @RequestHeader("Authorization") authorization: String,
    ): GoogleUserInfoDto?
}
