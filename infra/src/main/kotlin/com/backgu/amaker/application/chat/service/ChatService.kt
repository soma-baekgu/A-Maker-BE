package com.backgu.amaker.application.chat.service

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.infra.jpa.chat.entity.ChatEntity
import com.backgu.amaker.infra.jpa.chat.repository.ChatRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatService(
    private val chatRepository: ChatRepository,
) {
    @Transactional
    fun save(chat: Chat): Chat = chatRepository.save(ChatEntity.of(chat)).toDomain()

    fun getUnreadChatCount(
        chatRoomId: Long,
        lastReadChatId: Long?,
    ): Long =
        if (lastReadChatId == null) {
            chatRepository.countByChatRoomId(chatRoomId)
        } else {
            chatRepository.countByChatRoomIdAndLastReadChatIdGreaterThan(chatRoomId, lastReadChatId)
        }

    fun findAllByIds(chatIds: List<Long>): List<Chat> = chatRepository.findAllById(chatIds).map { it.toDomain() }

    fun getById(chatId: Long) = chatRepository.findByIdOrNull(chatId)?.toDomain() ?: throw BusinessException(StatusCode.CHAT_NOT_FOUND)
}
