package com.backgu.amaker.chat.dto.response

import com.backgu.amaker.chat.dto.ChatListDto

class ChatListResponse(
    val chatRoomId: Long,
    val cursor: Long,
    val size: Int,
    val chatList: List<ChatResponse>,
) {
    companion object {
        fun of(chatListDto: ChatListDto) =
            ChatListResponse(
                chatRoomId = chatListDto.chatRoomId,
                cursor = chatListDto.cursor,
                size = chatListDto.size,
                chatList = chatListDto.chatList.map { ChatResponse.of(it) },
            )
    }
}
