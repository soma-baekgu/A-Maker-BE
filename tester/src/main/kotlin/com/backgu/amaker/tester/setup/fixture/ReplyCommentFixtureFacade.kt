package com.backgu.amaker.tester.setup.fixture

import com.backgu.amaker.domain.chat.ChatRoomType
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.domain.event.ReplyEvent
import org.springframework.stereotype.Component

@Component
class ReplyCommentFixtureFacade(
    val replyCommentFixture: ReplyCommentFixture,
    val replyEventFixture: ReplyEventFixture,
    val chatRoomFixture: ChatRoomFixture,
    val chatFixture: ChatFixture,
    val eventAssignedUserFixture: EventAssignedUserFixture,
    val userFixture: UserFixture,
    private val workspaceFixture: WorkspaceFixture,
    private val workspaceUserFixture: WorkspaceUserFixture,
) {
    fun setUp(
        userId: String = "test-user-id",
        name: String = "김리더",
        workspaceId: Long = 1L,
    ): ReplyEvent {
        val workspace = workspaceFixture.createPersistedWorkspace(workspaceId, "test-workspace")
        workspaceUserFixture.createPersistedWorkspaceUser(workspace.id, userId)
        val user = userFixture.createPersistedUser(id = userId, name = name)
        val chatRoom = chatRoomFixture.createPersistedChatRoom(workspace.id, ChatRoomType.DEFAULT)
        val chat = chatFixture.createPersistedChat(chatRoom.id, userId, chatType = ChatType.REPLY)
        val replyEvent = replyEventFixture.createPersistedReplyEvent(chat.id)
        val eventAssignedUser = eventAssignedUserFixture.createPersistedEventAssignedUser(userId, replyEvent.id)
        return replyEvent
    }
}
