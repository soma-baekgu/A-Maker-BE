package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.ChatRoomUser
import com.backgu.amaker.chat.repository.ChatRoomUserRepository
import java.util.UUID

class ChatRoomUserFixture(
    private val chatRoomUserRepository: ChatRoomUserRepository,
) {
    fun testChatRoomUserSetUp() {
        chatRoomUserRepository.saveAll(
            listOf(
                ChatRoomUser(
                    chatRoomId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000001"),
                ),
                ChatRoomUser(
                    chatRoomId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                ),
                ChatRoomUser(
                    chatRoomId = 1L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000003"),
                ),
                ChatRoomUser(
                    chatRoomId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                ),
                ChatRoomUser(
                    chatRoomId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000001"),
                ),
                ChatRoomUser(
                    chatRoomId = 2L,
                    userId = UUID.fromString("00000000-0000-0000-0000-000000000003"),
                ),
            ),
        )
    }
}
