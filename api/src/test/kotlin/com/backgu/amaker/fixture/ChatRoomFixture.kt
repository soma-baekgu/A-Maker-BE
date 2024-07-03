package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.chat.domain.ChatRoomType
import com.backgu.amaker.chat.jpa.ChatRoomEntity
import com.backgu.amaker.chat.repository.ChatRoomRepository
import com.backgu.amaker.workspace.domain.Workspace
import org.springframework.stereotype.Component

@Component
class ChatRoomFixture(
    private val chatRoomRepository: ChatRoomRepository,
) {
    fun createPersistedChatRoom(
        workspaceId: Long,
        chatRoomType: ChatRoomType,
    ): ChatRoom =
        chatRoomRepository
            .save(
                ChatRoomEntity(workspaceId = workspaceId, chatRoomType = chatRoomType),
            ).toDomain()

    fun testChatRoomSetUp(
        count: Int,
        workspace: Workspace,
        chatRoomType: ChatRoomType? = null,
    ): List<ChatRoom> =
        (1..count).map {
            createPersistedChatRoom(workspaceId = workspace.id, chatRoomType = chatRoomType ?: ChatRoomType.GROUP)
        }

    fun deleteAll() {
        chatRoomRepository.deleteAll()
    }
}
