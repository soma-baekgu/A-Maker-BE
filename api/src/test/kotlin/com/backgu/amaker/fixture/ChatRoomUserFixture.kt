package com.backgu.amaker.fixture

import com.backgu.amaker.chat.jpa.ChatRoomUserEntity
import com.backgu.amaker.chat.repository.ChatRoomUserRepository
import org.springframework.stereotype.Component

@Component
class ChatRoomUserFixture(
    val chatRoomUserRepository: ChatRoomUserRepository,
) {
    fun createPersistedChatRoomUser(
        chatRoomId: Long,
        userIds: List<String>,
    ): List<ChatRoomUserEntity> =
        userIds.map {
            chatRoomUserRepository
                .save(
                    ChatRoomUserEntity(
                        chatRoomId = chatRoomId,
                        userId = it,
                    ),
                )
        }

    fun deleteAll() {
        chatRoomUserRepository.deleteAll()
    }
}
