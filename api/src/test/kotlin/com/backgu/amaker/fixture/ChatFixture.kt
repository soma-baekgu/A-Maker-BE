package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.Chat
import com.backgu.amaker.chat.domain.ChatType
import com.backgu.amaker.chat.jpa.ChatEntity
import com.backgu.amaker.chat.repository.ChatJpaRepository
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.random.Random

@Component
class ChatFixture(
    private val chatJpaRepository: ChatJpaRepository,
) {
    fun createPersistedChat(
        chatRoomId: Long,
        userId: String,
        message: String = "테스트 메시지 ${UUID.randomUUID()}",
    ): Chat =
        chatJpaRepository
            .save(
                ChatEntity(
                    chatRoomId = chatRoomId,
                    userId = userId,
                    content = message,
                    chatType = ChatType.GENERAL,
                ),
            ).toDomain()

    fun createPersistedChats(
        chatRoomId: Long,
        userId: String,
        count: Int = 10,
        messagePrefix: String = "테스트 메시지",
    ): List<Chat> =
        (0 until count).map {
            createPersistedChat(chatRoomId, userId, "$messagePrefix $it")
        }

    fun createPersistedRandomUserChats(
        chatRoomId: Long,
        userIds: List<String>,
        count: Int = 10,
        messagePrefix: String = "테스트 메시지",
    ): List<Chat> =
        (0 until count).map {
            createPersistedChat(chatRoomId, userIds[Random.nextInt(userIds.size)], "$messagePrefix $it")
        }

    fun deleteAll() {
        chatJpaRepository.deleteAll()
    }
}
