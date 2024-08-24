package com.backgu.amaker.infra.redis.chat.repository

import com.backgu.amaker.infra.redis.chat.data.ChatWithUserCache
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.stereotype.Repository

@Repository
class ChatPipelinedQueryRepository(
    private val redisChatTemplate: RedisTemplate<String, String>,
    private val timeObjectMapper: ObjectMapper,
) {
    companion object {
        const val PREFIX = "chatRoom:"

        fun key(chatRoomId: Long) = "$PREFIX$chatRoomId"

        const val AFTER_CHAT_SCRIPT =
            """
                local key = KEYS[1]
                local cursor = tonumber(ARGV[1])
                local count = tonumber(ARGV[2])
    
                local firstResult = redis.call('ZRANGE', key, 0, 0, 'WITHSCORES')
    
                if #firstResult > 0 then
                    local firstChat = firstResult[1]
                    local firstScore = tonumber(firstResult[2])  -- 첫 번째 요소의 score 값을 가져옴
                    
                    if firstScore > cursor then
                        local result = redis.call('ZRANGEBYSCORE', key, cursor + 1, math.huge, 'LIMIT', 0, count)
                        if #result > 0 then
                            return table.concat(result, ",")
                        else
                            return ""
                        end
                    else
                        return firstChat
                    end
                else
                    return nil
                end
            """
    }

    fun findAfterChats(
        chatRoomId: Long,
        cursor: Long,
        count: Int,
    ): List<ChatWithUserCache<*>>? {
        val result: List<String> =
            redisChatTemplate.execute(
                RedisScript.of(AFTER_CHAT_SCRIPT.trimIndent(), List::class.java as Class<List<String>>),
                listOf(key(chatRoomId)),
                cursor.toString(),
                count.toString(),
            )

        return result.map { chat ->
            timeObjectMapper.readValue(chat, ChatWithUserCache::class.java)
        }
    }
}
