package com.backgu.amaker.auth.test

import com.backgu.amaker.auth.dto.GoogleUserInfoDto
import com.backgu.amaker.auth.infra.GoogleApiClient

class FailedFakeGoogleApiClient : GoogleApiClient {
    override fun getUserInfo(authorization: String): GoogleUserInfoDto? = throw IllegalArgumentException("Failed to get user information")
}
