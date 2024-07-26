package com.backgu.amaker.api.auth.test

import com.backgu.amaker.api.auth.dto.oauth.google.GoogleUserInfoDto
import com.backgu.amaker.api.auth.infra.GoogleApiClient

class SuccessfulStubGoogleApiClient(
    email: String,
) : GoogleApiClient {
    private val googleUserInfo: GoogleUserInfoDto =
        GoogleUserInfoDto(
            id = "stubId",
            email = email,
            verifiedEmail = true,
            name = "stubName",
            givenName = "stubGivenName",
            picture = "stubPicture",
        )

    override fun getUserInfo(authorization: String): GoogleUserInfoDto = googleUserInfo
}
