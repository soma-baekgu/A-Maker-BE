package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.workspace.domain.Workspace
import com.backgu.amaker.workspace.domain.WorkspaceUser
import org.springframework.stereotype.Component

@Component
class WorkspaceFixtureFacade(
    val chatRoom: ChatRoomFixture,
    val chatRoomUser: ChatRoomUserFixture,
    val workspace: WorkspaceFixture,
    val workspaceUser: WorkspaceUserFixture,
    val user: UserFixture,
) {
    fun setUp() {
        val leader: User = user.createPersistedUser(name = "김리더", email = "leader@amaker.com")
        val workspace: Workspace = workspace.createPersistedWorkspace(name = "테스트 워크스페이스")
        val members: List<User> = user.createPersistedUsers(10)

        val workspaceUsers: List<WorkspaceUser> =
            workspaceUser.createPersistedWorkspaceUser(
                workspaceId = workspace.id,
                leaderId = leader.id,
                memberIds = members.map { it.id },
            )

        val chatRooms: List<ChatRoom> =
            chatRoom.testChatRoomSetUp(count = 10, workspace = workspace)

        chatRooms.forEach { chatRoom ->
            chatRoomUser.createPersistedChatRoomUser(
                chatRoomId = chatRoom.id,
                userIds = workspaceUsers.map { it.userId },
            )
        }
    }

    fun deleteAll() {
        chatRoom.deleteAll()
        chatRoomUser.deleteAll()
        workspace.deleteAll()
        workspaceUser.deleteAll()
        user.deleteAll()
    }
}
