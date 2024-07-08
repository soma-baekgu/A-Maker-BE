package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.workspace.domain.Workspace
import com.backgu.amaker.workspace.domain.WorkspaceUser
import org.springframework.stereotype.Component

@Component
class ChatFixtureFacade(
    val chatRoom: ChatRoomFixture,
    val chat: ChatFixture,
    val chatRoomUser: ChatRoomUserFixture,
    val workspace: WorkspaceFixture,
    val workspaceUser: WorkspaceUserFixture,
    val user: UserFixture,
) {
    fun setUp(
        userId: String = "test-user-id",
        name: String = "김리더",
        email: String = "leader@amaker.com",
        workspaceName: String = "테스트 워크스페이스",
    ): ChatRoom {
        val leader: User = user.createPersistedUser(id = userId, name = name, email = email)
        val workspace: Workspace = workspace.createPersistedWorkspace(name = workspaceName)
        val members: List<User> = user.createPersistedUsers(10)

        val workspaceUsers: List<WorkspaceUser> =
            workspaceUser.createPersistedWorkspaceUser(
                workspaceId = workspace.id,
                leaderId = leader.id,
                memberIds = members.map { it.id },
            )

        val chatRoom = chatRoom.testGroupChatRoomSetUp(workspace = workspace)

        chatRoomUser.createPersistedChatRoomUser(
            chatRoomId = chatRoom.id,
            userIds = workspaceUsers.map { it.userId },
        )

        chat.createPersistedChats(
            chatRoomId = chatRoom.id,
            userId = leader.id,
            count = 100,
        )

        return chatRoom
    }

    fun deleteAll() {
        chatRoom.deleteAll()
        chatRoomUser.deleteAll()
        workspace.deleteAll()
        workspaceUser.deleteAll()
        user.deleteAll()
        chat.deleteAll()
    }
}
