package com.backgu.amaker.chat.dto

import com.backgu.amaker.chat.domain.Chat

data class ChatListDto(
    val chatRoomId: Long,
    val cursor: Long,
    val size: Int,
    val chatList: List<ChatDto>,
) {
    companion object {
        fun of(
            chatQuery: ChatQuery,
            chatList: List<Chat>,
        ): ChatListDto =
            ChatListDto(
                chatRoomId = chatQuery.chatRoomId,
                cursor = chatQuery.cursor,
                size = chatList.size,
                chatList = chatList.map { ChatDto.of(it) },
            )
    }
}
