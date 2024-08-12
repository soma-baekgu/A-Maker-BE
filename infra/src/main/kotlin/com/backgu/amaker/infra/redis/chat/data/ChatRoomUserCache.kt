package com.backgu.amaker.infra.redis.chat.data

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("ChatRoom:User")
class ChatRoomUserCache(
    @Id
    val id: Long,
    val userIds: MutableSet<String> = mutableSetOf(),
) {
    fun addUser(userId: String) {
        userIds.add(userId)
    }

    fun removeUser(userId: String) {
        userIds.remove(userId)
    }
}
