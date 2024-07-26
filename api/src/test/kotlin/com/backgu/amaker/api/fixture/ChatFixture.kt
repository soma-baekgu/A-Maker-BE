package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.infra.jpa.chat.entity.ChatEntity
import com.backgu.amaker.infra.jpa.chat.repository.ChatJpaRepository
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
    ): List<Chat> {
        val chatEntities =
            (0 until count).map {
                ChatEntity(
                    chatRoomId = chatRoomId,
                    userId = userId,
                    content = "$messagePrefix $it",
                    chatType = ChatType.GENERAL,
                )
            }
        return chatJpaRepository.saveAll(chatEntities).map { it.toDomain() }
    }

    fun createPersistedRandomUserChats(
        chatRoomId: Long,
        userIds: List<String>,
        count: Int = 10,
        messagePrefix: String = "테스트 메시지",
    ): List<Chat> {
        val chatEntities =
            (0 until count).map {
                ChatEntity(
                    chatRoomId = chatRoomId,
                    userId = userIds[Random.nextInt(userIds.size)],
                    content = "$messagePrefix $it",
                    chatType = ChatType.GENERAL,
                )
            }
        return chatJpaRepository.saveAll(chatEntities).map { it.toDomain() }
    }

    fun deleteAll() {
        chatJpaRepository.deleteAll()
    }
}
