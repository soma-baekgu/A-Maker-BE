package com.backgu.amaker.chat.dto.request

import com.backgu.amaker.chat.dto.GeneralChatCreateDto

data class GeneralChatCreateRequest(
    val userId: String,
    val content: String,
) {
    fun toDto() =
        GeneralChatCreateDto(
            userId = userId,
            content = content,
        )
}
