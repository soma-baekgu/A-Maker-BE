package com.backgu.amaker.chat.dto

data class ChatListDto(
    val chatRoomId: Long,
    val cursor: Long,
    val size: Int,
    val chatList: List<ChatWithUser<*>>,
) {
    companion object {
        fun of(
            chatQuery: ChatQuery,
            chatList: List<ChatWithUser<*>>,
        ): ChatListDto =
            ChatListDto(
                chatRoomId = chatQuery.chatRoomId,
                cursor = chatQuery.cursor,
                size = chatList.size,
                chatList = chatList,
            )
    }
}
