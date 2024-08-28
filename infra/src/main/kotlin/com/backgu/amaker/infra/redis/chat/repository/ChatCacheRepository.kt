package com.backgu.amaker.infra.redis.chat.repository

import com.backgu.amaker.infra.redis.chat.data.ChatWithUserCache
import com.backgu.amaker.infra.redis.chat.data.EventChatWithUserCache
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class ChatCacheRepository(
    private val redisChatTemplate: RedisTemplate<String, String>,
    private val timeObjectMapper: ObjectMapper,
) {
    companion object {
        const val PREFIX = "chatRoom:"

        fun key(chatRoomId: Long) = "$PREFIX$chatRoomId"
    }

    fun saveChat(
        chatRoomId: Long,
        chat: ChatWithUserCache<*>,
    ) {
        redisChatTemplate
            .opsForZSet()
            .add(key(chatRoomId), timeObjectMapper.writeValueAsString(chat), chat.id.toDouble())

        redisChatTemplate
            .opsForZSet()
            .removeRange(key(chatRoomId), 0, -76)
    }

    fun updateFinishedCount(
        chatRoomId: Long,
        chatId: Long,
        finishedCount: Int,
    ) {
        val chat =
            redisChatTemplate
                .opsForZSet()
                .rangeByScore(key(chatRoomId), chatId.toDouble(), chatId.toDouble(), 0, 1)
                ?.firstOrNull() ?: return

        val chatWithUserCache = timeObjectMapper.readValue(chat, ChatWithUserCache::class.java)

        if (chatWithUserCache is EventChatWithUserCache) {
            val updatedChat = chatWithUserCache.updateFinishedCount(finishedCount)

            deleteChat(chatRoomId, chatId)

            redisChatTemplate
                .opsForZSet()
                .add(key(chatRoomId), timeObjectMapper.writeValueAsString(updatedChat), chatId.toDouble())
        }
    }

    fun deleteChat(
        chatRoomId: Long,
        chatId: Long,
    ) {
        redisChatTemplate
            .opsForZSet()
            .removeRangeByScore(key(chatRoomId), chatId.toDouble(), chatId.toDouble())
    }

    fun findChat(
        chatRoomId: Long,
        chatId: Long,
    ): ChatWithUserCache<*>? {
        val chatScoreRange =
            redisChatTemplate
                .opsForZSet()
                .rangeByScore(key(chatRoomId), chatId.toDouble(), chatId.toDouble(), 0, 1)
                ?.firstOrNull()

        return chatScoreRange?.let {
            timeObjectMapper.readValue(it, ChatWithUserCache::class.java)
        }
    }

    fun findPreviousChats(
        chatRoomId: Long,
        cursor: Long,
        count: Int,
    ): List<ChatWithUserCache<*>> =
        redisChatTemplate
            .opsForZSet()
            .reverseRangeByScore(key(chatRoomId), Double.NEGATIVE_INFINITY, (cursor - 1).toDouble(), 0, count.toLong())
            ?.map { chat ->
                timeObjectMapper.readValue(chat, ChatWithUserCache::class.java)
            } ?: emptyList()

    fun findAfterChats(
        chatRoomId: Long,
        cursor: Long,
        count: Int,
    ): List<ChatWithUserCache<*>> =
        redisChatTemplate
            .opsForZSet()
            .rangeByScore(key(chatRoomId), (cursor + 1).toDouble(), Double.POSITIVE_INFINITY, 0, count.toLong())
            ?.map { chat ->
                timeObjectMapper.readValue(chat, ChatWithUserCache::class.java)
            } ?: emptyList()

    fun findFirstChat(chatRoomId: Long): ChatWithUserCache<*>? =
        redisChatTemplate
            .opsForZSet()
            .range(key(chatRoomId), 0, 0)
            ?.firstOrNull()
            ?.let { chat ->
                timeObjectMapper.readValue(chat, ChatWithUserCache::class.java)
            }
}
