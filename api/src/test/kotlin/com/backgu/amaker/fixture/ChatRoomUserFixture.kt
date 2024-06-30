package com.backgu.amaker.fixture

import com.backgu.amaker.chat.jpa.ChatRoomUserEntity
import com.backgu.amaker.chat.repository.ChatRoomUserRepository
import java.util.UUID

class ChatRoomUserFixture(
    private val chatRoomUserRepository: ChatRoomUserRepository,
) {
    fun testChatRoomUserSetUp() {
        chatRoomUserRepository.saveAll(
            listOf(
                ChatRoomUserEntity(
                    chatRoomId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000001"),
                ),
                ChatRoomUserEntity(
                    chatRoomId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                ),
                ChatRoomUserEntity(
                    chatRoomId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000003"),
                ),
                ChatRoomUserEntity(
                    chatRoomId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                ),
                ChatRoomUserEntity(
                    chatRoomId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000001"),
                ),
                ChatRoomUserEntity(
                    chatRoomId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000003"),
                ),
            ),
        )
    }
}
