package com.backgu.amaker.chat.service

import com.backgu.amaker.chat.domain.Chat
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

    fun getChatList(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<Chat> =
        chatRepository
            .findTopByChatRoomIdLittleThanCursorLimitCount(chatRoomId, cursor, size)
            .asReversed()
            .map { it.toDomain() }
}
