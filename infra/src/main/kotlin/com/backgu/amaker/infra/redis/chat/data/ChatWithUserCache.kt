package com.backgu.amaker.infra.redis.chat.data

import com.backgu.amaker.domain.chat.ChatType
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.LocalDateTime

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "chatType",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = DefaultChatWithUserCache::class, name = "GENERAL"),
    JsonSubTypes.Type(value = DefaultChatWithUserCache::class, name = "FILE"),
    JsonSubTypes.Type(value = DefaultChatWithUserCache::class, name = "IMAGE"),
    JsonSubTypes.Type(value = EventChatWithUserCache::class, name = "REPLY"),
    JsonSubTypes.Type(value = EventChatWithUserCache::class, name = "REACTION"),
    JsonSubTypes.Type(value = EventChatWithUserCache::class, name = "TASK"),
)
interface ChatWithUserCache<T> {
    val id: Long
    val chatRoomId: Long
    val content: T
    val chatType: ChatType
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
    val userId: String
}
