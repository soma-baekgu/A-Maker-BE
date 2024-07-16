package com.backgu.amaker.chat.dto

data class ChatListDto(
    val chatRoomId: Long,
    val cursor: Long,
    val size: Int,
    val chatList: List<ChatDto>,
) {
    companion object {
        fun of(
            chatQuery: ChatQuery,
            chatList: List<ChatDto>,
        ): ChatListDto =
            ChatListDto(
                chatRoomId = chatQuery.chatRoomId,
                cursor = chatQuery.cursor,
                size = chatList.size,
                chatList = chatList,
            )
    }
}
