package com.backgu.amaker.api.user.dto

import com.backgu.amaker.domain.user.User

data class EmailExistsDto(
    val exists: Boolean,
) {
    companion object {
        fun of(user: User?): EmailExistsDto = EmailExistsDto(exists = user != null)
    }
}
