package com.backgu.amaker.api.chat.dto

import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.user.User

class ChatRoomsViewDto(
    val chatRooms: List<ChatRoomWithUserAndLastChatDto>,
    val totalUnreadChatCount: Long = 0,
) {
    companion object {
        fun of(
            chatRooms: List<ChatRoom>,
            chatRoomUsers: Map<Long, List<String>>,
            chatRoomWithLastChat: Map<Long, Chat>,
            users: Map<String, User>,
            unreadChatCountMap: Map<Long, Long>,
        ): ChatRoomsViewDto {
            val chatRoomWithUserAndLastChatDtos =
                chatRooms.map { chatRoom ->
                    ChatRoomWithUserAndLastChatDto.of(
                        chatRoomId = chatRoom.id,
                        chatRoomName = chatRoom.name,
                        participants =
                            chatRoomUsers[chatRoom.id]?.mapNotNull { userId ->
                                users[userId]?.let { UserDto.of(it) }
                            } ?: emptyList(),
                        lastChat =
                            chatRoomWithLastChat[chatRoom.id]
                                ?.let { chat ->
                                    users[chat.userId]?.let { user ->
                                        DefaultChatWithUserDto.of(chat, user)
                                    }
                                },
                        unreadChatCount = unreadChatCountMap[chatRoom.id] ?: 0,
                    )
                }

            val totalUnreadChatCount = chatRoomWithUserAndLastChatDtos.sumOf { it.unreadChatCount ?: 0 }

            return ChatRoomsViewDto(
                chatRooms = chatRoomWithUserAndLastChatDtos,
                totalUnreadChatCount = totalUnreadChatCount,
            )
        }
    }
}
