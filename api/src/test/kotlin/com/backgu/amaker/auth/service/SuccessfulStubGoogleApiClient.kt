package com.backgu.amaker.auth.service

import com.backgu.amaker.auth.dto.GoogleUserInfoDto
import com.backgu.amaker.auth.infra.GoogleApiClient

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
