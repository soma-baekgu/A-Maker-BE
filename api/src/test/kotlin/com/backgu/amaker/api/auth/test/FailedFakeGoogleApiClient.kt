package com.backgu.amaker.api.auth.test

import com.backgu.amaker.api.auth.dto.oauth.google.GoogleUserInfoDto
import com.backgu.amaker.api.auth.infra.GoogleApiClient

class FailedFakeGoogleApiClient : GoogleApiClient {
    override fun getUserInfo(authorization: String): GoogleUserInfoDto? = throw IllegalArgumentException("Failed to get user information")
}
