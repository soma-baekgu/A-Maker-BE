package com.backgu.amaker.chat.dto.request

import com.backgu.amaker.chat.dto.GeneralChatCreateDto

data class GeneralChatCreateRequest(
    val content: String,
) {
    fun toDto() =
        GeneralChatCreateDto(
            content = content,
        )
}
