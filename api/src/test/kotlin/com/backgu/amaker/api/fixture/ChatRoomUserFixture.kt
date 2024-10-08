package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.chat.ChatRoomUser
import com.backgu.amaker.infra.jpa.chat.entity.ChatRoomUserEntity
import com.backgu.amaker.infra.jpa.chat.repository.ChatRoomUserRepository
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
