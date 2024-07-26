package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatRoomType
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.infra.jpa.chat.entity.ChatRoomEntity
import com.backgu.amaker.infra.jpa.chat.repository.ChatRoomRepository
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
                ChatRoomEntity(workspaceId = workspaceId, name = "General", chatRoomType = chatRoomType),
            ).toDomain()

    fun testGroupChatRoomSetUp(workspace: Workspace): ChatRoom =
        createPersistedChatRoom(workspaceId = workspace.id, chatRoomType = ChatRoomType.DEFAULT)

    fun save(chatRoom: ChatRoom): ChatRoom = chatRoomRepository.save(ChatRoomEntity.of(chatRoom)).toDomain()

    fun deleteAll() {
        chatRoomRepository.deleteAll()
    }
}
