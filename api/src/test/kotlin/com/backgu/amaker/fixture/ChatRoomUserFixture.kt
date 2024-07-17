package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.ChatRoomUser
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
    ): List<ChatRoomUser> =
        userIds
            .map {
                chatRoomUserRepository
                    .save(
                        ChatRoomUserEntity(
                            chatRoomId = chatRoomId,
                            userId = it,
                        ),
                    )
            }.map { it.toDomain() }

    fun save(chatRoomUser: ChatRoomUser): ChatRoomUser = chatRoomUserRepository.save(ChatRoomUserEntity.of(chatRoomUser)).toDomain()

    fun deleteAll() {
        chatRoomUserRepository.deleteAll()
    }
}
