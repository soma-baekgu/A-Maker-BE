package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.chat.repository.ChatRoomRepository

class ChatRoomFixture(
    private val chatRoomRepository: ChatRoomRepository,
) {
    fun testChatRoomSetUp() {
        chatRoomRepository.saveAll(
            listOf(
                ChatRoom(workspaceId = 1L, chatRoomType = ChatRoomType.GROUP),
                ChatRoom(workspaceId = 2L, chatRoomType = ChatRoomType.GROUP),
            ),
        )
    }
}
