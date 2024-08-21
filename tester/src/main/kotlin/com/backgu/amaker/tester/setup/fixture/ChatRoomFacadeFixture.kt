package com.backgu.amaker.tester.setup.fixture

import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.domain.workspace.WorkspaceUser
import org.springframework.stereotype.Component

@Component
class ChatRoomFacadeFixture(
    val chatRoomFixture: ChatRoomFixture,
    val chatRoomUserFixture: ChatRoomUserFixture,
    val workspaceFixture: WorkspaceFixture,
    val workspaceUserFixture: WorkspaceUserFixture,
    val userFixture: UserFixture,
    val chatFixture: ChatFixture,
) {
    fun setUp(
        userId: String = "test-user-id",
        name: String = "김리더",
        workspaceName: String = "테스트 워크스페이스",
    ): ChatRoomFixtureDto {
        val leader: User = userFixture.createPersistedUser(id = userId, name = name)
        val workspace: Workspace = workspaceFixture.createPersistedWorkspace(name = workspaceName)
        val members: List<User> = userFixture.createPersistedUsers(10)

        val workspaceUsers: List<WorkspaceUser> =
            workspaceUserFixture.createPersistedWorkspaceUser(
                workspaceId = workspace.id,
                leaderId = leader.id,
                memberIds = members.map { it.id },
            )

        val chatRoom = chatRoomFixture.testGroupChatRoomSetUp(workspace = workspace)

        chatRoomUserFixture.createPersistedChatRoomUser(
            chatRoomId = chatRoom.id,
            userIds = workspaceUsers.map { it.userId },
        )

        return ChatRoomFixtureDto(workspace, chatRoom, members)
    }

    fun deleteAll() {
        chatRoomFixture.deleteAll()
        chatRoomUserFixture.deleteAll()
        workspaceFixture.deleteAll()
        workspaceUserFixture.deleteAll()
        userFixture.deleteAll()
    }
}
