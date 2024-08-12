package com.backgu.amaker.application.chat.service

import com.backgu.amaker.infra.redis.chat.data.ChatRoomUserCache
import com.backgu.amaker.infra.redis.chat.repository.ChatRoomUserCacheRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChatRoomUserCacheService(
    private val chatRoomUserCacheRepository: ChatRoomUserCacheRepository,
) {
    fun findUserIds(chatRoomId: Long): Set<String> = chatRoomUserCacheRepository.findByIdOrNull(chatRoomId)?.userIds ?: emptySet()

    fun addUser(
        chatRoomId: Long,
        userId: String,
    ) {
        val chatRoomUserCache =
            chatRoomUserCacheRepository.findByIdOrNull(chatRoomId)
                ?: ChatRoomUserCache(chatRoomId)

        chatRoomUserCache.addUser(userId)
        chatRoomUserCacheRepository.save(chatRoomUserCache)
    }

    fun removeUser(
        chatRoomId: Long,
        userId: String,
    ) {
        val chatRoomUserCache =
            chatRoomUserCacheRepository.findByIdOrNull(chatRoomId)
                ?: ChatRoomUserCache(chatRoomId)

        chatRoomUserCache.removeUser(userId)
        chatRoomUserCacheRepository.save(chatRoomUserCache)
    }
}
