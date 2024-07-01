package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.chat.jpa.ChatRoomEntity
import com.backgu.amaker.chat.repository.ChatRoomRepository

class ChatRoomFixture(
    private val chatRoomRepository: ChatRoomRepository,
) {
    fun testChatRoomSetUp() {
        chatRoomRepository.saveAll(
            listOf(
                ChatRoomEntity(workspaceId = 1L, chatRoomType = ChatRoomType.GROUP),
                ChatRoomEntity(workspaceId = 2L, chatRoomType = ChatRoomType.GROUP),
            ),
        )
    }
}
