package com.backgu.amaker.api.chat.service.query

import com.backgu.amaker.api.chat.dto.DefaultChatWithUserDto
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
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
    ): List<DefaultChatWithUserDto> =
        chatRepository
            .findTopByChatRoomIdLittleThanCursorLimitCountWithUser(chatRoomId, cursor, size)
            .asReversed()
            .map { DefaultChatWithUserDto.of(it) }

    fun findAfterChatList(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<DefaultChatWithUserDto> =
        chatRepository
            .findTopByChatRoomIdGreaterThanCursorLimitCountWithUser(chatRoomId, cursor, size)
            .map { DefaultChatWithUserDto.of(it) }

    fun getOneWithUser(chatId: Long?): DefaultChatWithUserDto =
        DefaultChatWithUserDto.of(
            chatId?.let { chatRepository.findByIdWithUser(it) }
                ?: throw BusinessException(StatusCode.CHAT_NOT_FOUND),
        )
}
