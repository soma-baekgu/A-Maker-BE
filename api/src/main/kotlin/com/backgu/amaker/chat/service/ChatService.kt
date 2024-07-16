package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.Chat
import com.backgu.amaker.chat.dto.ChatDto
import com.backgu.amaker.chat.jpa.ChatEntity
import com.backgu.amaker.chat.repository.ChatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatService(
    private val chatRepository: ChatRepository,
) {
    @Transactional
    fun save(chat: Chat): Chat = chatRepository.save(ChatEntity.of(chat)).toDomain()

    fun findPreviousChatList(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatDto> =
        chatRepository
            .findTopByChatRoomIdLittleThanCursorLimitCountWithUser(chatRoomId, cursor, size)
            .asReversed()

    fun findAfterChatList(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatDto> =
        chatRepository
            .findTopByChatRoomIdGreaterThanCursorLimitCountWithUser(chatRoomId, cursor, size)
}
