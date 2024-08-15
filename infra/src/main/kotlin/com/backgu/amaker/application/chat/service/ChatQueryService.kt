package com.backgu.amaker.application.chat.service

import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.chat.DefaultChatWithUser
import com.backgu.amaker.infra.jpa.chat.repository.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatQueryService(
    private val chatRepository: ChatRepository,
) {
    fun findPreviousChatList(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<DefaultChatWithUser> =
        chatRepository
            .findTopByChatRoomIdLittleThanCursorLimitCountWithUser(chatRoomId, cursor, size)
            .asReversed()
            .map { it.toDomain() }

    fun findAfterChatList(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<DefaultChatWithUser> =
        chatRepository
            .findTopByChatRoomIdGreaterThanCursorLimitCountWithUser(chatRoomId, cursor, size)
            .map { it.toDomain() }

    fun getOneWithUser(chatId: Long?): DefaultChatWithUser =
        chatId
            ?.let { chatRepository.findByIdWithUser(it) }
            ?.toDomain()
            ?: throw BusinessException(StatusCode.CHAT_NOT_FOUND)
}
