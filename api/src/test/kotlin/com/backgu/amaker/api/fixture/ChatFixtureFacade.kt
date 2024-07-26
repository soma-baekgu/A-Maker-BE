package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.chat.ChatRoomUser
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.domain.workspace.WorkspaceUser
import org.springframework.stereotype.Component

@Component
class ChatFixtureFacade(
    val chatRoomFixture: ChatRoomFixture,
    val chatFixture: ChatFixture,
    val chatRoomUserFixture: ChatRoomUserFixture,
    val workspaceFixture: WorkspaceFixture,
    val workspaceUserFixture: WorkspaceUserFixture,
    val userFixture: UserFixture,
) {
    fun setUp(
        userId: String = "test-user-id",
        name: String = "김리더",
        email: String = "$userId@email.com",
        workspaceName: String = "테스트 워크스페이스",
    ): ChatRoom {
        val leader: User = userFixture.createPersistedUser(id = userId, name = name, email = email)
        val workspace: Workspace = workspaceFixture.createPersistedWorkspace(name = workspaceName)
        val members: List<User> = userFixture.createPersistedUsers(10)

        val workspaceUsers: List<WorkspaceUser> =
            workspaceUserFixture.createPersistedWorkspaceUser(
                workspaceId = workspace.id,
                leaderId = leader.id,
                memberIds = members.map { it.id },
            )

        val chatRoom = chatRoomFixture.testGroupChatRoomSetUp(workspace = workspace)

        val chatRoomUsers: List<ChatRoomUser> =
            chatRoomUserFixture.createPersistedChatRoomUser(
                chatRoomId = chatRoom.id,
                userIds = workspaceUsers.map { it.userId },
            )

        val chats: List<Chat> =
            chatFixture.createPersistedChats(
                chatRoomId = chatRoom.id,
                userId = leader.id,
                count = 100,
            )

        val lastChatIdChatRoom = chatRoomFixture.save(chatRoom.updateLastChatId(chats.last()))
        chatRoomUsers.forEach { chatRoomUserFixture.save(it.readLastChatOfChatRoom(lastChatIdChatRoom)) }

        return chatRoom
    }

    fun deleteAll() {
        chatRoomFixture.deleteAll()
        chatRoomUserFixture.deleteAll()
        workspaceFixture.deleteAll()
        workspaceUserFixture.deleteAll()
        userFixture.deleteAll()
        chatFixture.deleteAll()
    }
}
