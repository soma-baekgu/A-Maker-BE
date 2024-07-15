package com.backgu.amaker.user.dto

import com.backgu.amaker.user.domain.User

data class EmailExistsDto(
    val exists: Boolean,
) {
    companion object {
        fun of(user: User?): EmailExistsDto = EmailExistsDto(exists = user != null)
    }
}
