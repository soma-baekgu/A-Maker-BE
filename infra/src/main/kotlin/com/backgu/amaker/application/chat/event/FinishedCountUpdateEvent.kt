package com.backgu.amaker.application.chat.event

data class FinishedCountUpdateEvent(
    val chatId: Long,
) {
    companion object {
        fun of(chatId: Long) =
            FinishedCountUpdateEvent(
                chatId = chatId,
            )
    }
}
