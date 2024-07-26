package com.backgu.amaker.api.chat.service

import com.backgu.amaker.api.chat.dto.ChatWithUserDto
import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.api.common.exception.StatusCode
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.infra.jpa.chat.entity.ChatEntity
import com.backgu.amaker.infra.jpa.chat.repository.ChatRepository
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
    ): List<ChatWithUserDto> =
        chatRepository
            .findTopByChatRoomIdLittleThanCursorLimitCountWithUser(chatRoomId, cursor, size)
            .asReversed()
            .map { ChatWithUserDto.of(it) }

    fun findAfterChatList(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatWithUserDto> =
        chatRepository
            .findTopByChatRoomIdGreaterThanCursorLimitCountWithUser(chatRoomId, cursor, size)
            .map { ChatWithUserDto.of(it) }

    fun getOneWithUser(chatId: Long?): ChatWithUserDto =
        ChatWithUserDto.of(
            chatId?.let { chatRepository.findByIdWithUser(it) }
                ?: throw BusinessException(StatusCode.CHAT_NOT_FOUND),
        )

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
}
