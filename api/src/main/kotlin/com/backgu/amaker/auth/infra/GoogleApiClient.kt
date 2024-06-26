package com.backgu.amaker.auth.infra

import com.backgu.amaker.auth.dto.GoogleUserInfoDto
import com.backgu.amaker.config.CaughtHttpExchange
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange

@CaughtHttpExchange
interface GoogleApiClient {
    @GetExchange("/oauth2/v2/userinfo")
    fun getUserInfo(
        @RequestHeader("Authorization") authorization: String,
    ): GoogleUserInfoDto?
}
