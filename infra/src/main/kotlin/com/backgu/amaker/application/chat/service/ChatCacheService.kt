package com.backgu.amaker.application.chat.service

import com.backgu.amaker.infra.redis.chat.data.ChatWithUserCache
import com.backgu.amaker.infra.redis.chat.repository.ChatCacheRepository
import org.springframework.stereotype.Service

@Service
class ChatCacheService(
    private val chatCacheRepository: ChatCacheRepository,
) {
    fun saveChat(
        chatRoomId: Long,
        chat: ChatWithUserCache<*>,
    ) {
        chatCacheRepository.saveChat(chatRoomId, chat)
    }

    fun updateFinishedCount(
        chatRoomId: Long,
        chatId: Long,
        finishedCount: Int,
    ) {
        chatCacheRepository.updateFinishedCount(
            chatRoomId,
            chatId,
            finishedCount,
        )
    }

    fun findChat(
        chatRoomId: Long,
        chatId: Long,
    ): ChatWithUserCache<*>? = chatCacheRepository.findChat(chatRoomId, chatId)

    fun findPreviousChats(
        chatRoomId: Long,
        cursor: Long,
        count: Int,
    ): List<ChatWithUserCache<*>> = chatCacheRepository.findPreviousChats(chatRoomId, cursor, count)

    fun findAfterChats(
        chatRoomId: Long,
        cursor: Long,
        count: Int,
    ): List<ChatWithUserCache<*>> = chatCacheRepository.findAfterChats(chatRoomId, cursor, count)

    fun findFirstChat(chatRoomId: Long): ChatWithUserCache<*>? = chatCacheRepository.findFirstChat(chatRoomId)
}
