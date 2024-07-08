package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.Chat
import com.backgu.amaker.chat.domain.ChatType
import com.backgu.amaker.chat.jpa.ChatEntity
import com.backgu.amaker.chat.repository.ChatRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ChatFixture(
    private val chatRepository: ChatRepository,
) {
    fun createPersistedChat(
        chatRoomId: Long,
        userId: String,
        message: String = "테스트 메시지 ${UUID.randomUUID()}",
    ): Chat =
        chatRepository
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

    fun deleteAll() {
        chatRepository.deleteAll()
    }
}
